import subprocess
import numpy as np
import matplotlib.pyplot as plt
import imageio
import sys
import os
import imageio.v2 as imageio


import subprocess

def run_java_print_2d(input_file):
    # Execute Java code to print the 2D array
    process = subprocess.Popen(['java', 'print_2d', input_file], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = process.communicate()

    # Check if Java execution was successful
    if process.returncode != 0:
        print("Error executing Java code:")
        print(stderr.decode('utf-8'))
        return None
    
    # Extract the printed array from Java output
    output_lines = stdout.decode('utf-8').splitlines()
    frames = []
    current_frame = []
    for line in output_lines:
        if line.startswith("Frame"):
            if current_frame:
                frames.append(current_frame)
            current_frame = []
        else:
            try:
                frame_data = [float(value) for value in line.split()]
                current_frame.append(frame_data)
            except ValueError:
                # Skip lines that cannot be converted to float
                pass
    if current_frame:
        frames.append(current_frame)
    return frames



def plot_heatmap(array_data, frame_num):
    if array_data is None:
        return
    
    # Convert array to numpy array and transpose it
    data = np.array(array_data).T  # Transpose the array
    
    # Get the dimensions of the transposed array
    rows, cols = data.shape
    
    # Create meshgrid for plotting
    x = np.arange(cols)
    y = np.arange(rows)
    X, Y = np.meshgrid(x, y)
    
    # Calculate the step size for the y-axis based on the difference between the first two x points
    step_size_x = 1  # Default step size if there's only one column
    if cols > 1:
        step_size_x = x[1] - x[0]  # Calculate the step size based on the difference between the first two x points
    
    # Plot filled contour for heatmap
    plt.figure(figsize=(10, 6))
    cmap = 'coolwarm'
    heatmap = plt.contourf(X, Y, data, cmap=cmap, levels=50)
    
    # Overlay contour lines with corresponding values
    levels = np.linspace(np.min(data), np.max(data), num=10)
    contours = plt.contour(X, Y, data, levels=levels, colors='k', linestyles='solid', linewidths=0.5)
    plt.clabel(contours, inline=True, fontsize=8, fmt='%1.2f')
    
    # Color bar with separate color map
    cbar = plt.colorbar(heatmap, ticks=np.linspace(np.min(data), np.max(data), num=5))
    cbar.set_label('Heat Index')
    
    plt.xlabel('Time index (iterations)')  # Adjusted x-axis label
    plt.ylabel('Metal Strip (pixels)')  # Adjusted y-axis label
    
    # Set the y-axis ticks based on the calculated step size
    plt.yticks(np.arange(0, rows, step_size_x), np.arange(rows - 1, -1, -step_size_x))
    
    plt.title(f'Heatmap of Data (Frame {frame_num})')  # Title of the plot
    
    # Save the plot as an image
    plt.savefig(f'frame_{frame_num}.png')
    plt.close()

def create_movie(frames, output_file):
    # Create list to store image filenames
    images = []

    # Generate plots for each frame and save as images
    for i, frame in enumerate(frames, start=1):
        plot_heatmap(frame, i)
        images.append(f'frame_{i}.png')

    # Create GIF from images
    with imageio.get_writer(output_file, mode='I') as writer:
        for filename in images:
            image = imageio.imread(filename)
            writer.append_data(image)

    # Clean up image files
    for filename in set(images):
        os.remove(filename)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python generate_movie.py <all frames file> <movie output file>")
        sys.exit(1)

    input_file = sys.argv[1]
    output_file = sys.argv[2]

    # Run Java program to get frame data
    frames = run_java_print_2d(input_file)

    if frames is None:
        print("Error: Failed to get frame data.")
        sys.exit(1)

    # Create movie from frames
    create_movie(frames, output_file)
