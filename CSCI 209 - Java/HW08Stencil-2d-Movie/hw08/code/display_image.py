import argparse  
import numpy as np  
import matplotlib.pyplot as plt  
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
    array_lines = output_lines[4:]  # Skip first 4 lines (metadata)
   
    # Parse the array data, skipping non-numeric lines
    array_data = []
    for line in array_lines:
        values = line.split()
        try:
            row_data = [float(value) for value in values]
            array_data.append(row_data)
        except ValueError:
            pass  # Skip non-numeric lines
    
    return array_data

def plot_heatmap(array_data):
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
    
    plt.title('Heatmap of Data')  # Title of the plot
    
    # Show the plot
    plt.show()

if __name__ == "__main__":
    # Parse command-line arguments
    import sys

    if len(sys.argv) != 2:
        print("Usage: python display_image.py <input file>")
        sys.exit(1)

    # Run Java code to print the 2D array
    array_data = run_java_print_2d(sys.argv[1])

    # Plot heatmap
    plot_heatmap(array_data)
