import matplotlib.pyplot as plt

def plot_data(data_file, output_png):
    # Initialize lists to store data
    N = []
    T_W = []
    T = []
    T_IO = []

    # Parse data from the file
    with open(data_file, 'r') as f:
        header_skipped = False
        for line in f:
            if not header_skipped:
                header_skipped = True
                continue  # Skip the header line

            values = line.strip().split()
            if len(values) >= 4:  # Check if line contains enough values
                N.append(int(values[0]))
                T.append(float(values[1]))
                T_W.append(float(values[2]))
                T_IO.append(float(values[3]))

    # Plot the data
    plt.plot(N, T, label='Total Time')
    plt.plot(N, T_W, label='Work Time')
    plt.plot(N, T_IO, label='IO Time')

    # Set labels and title
    plt.xlabel('N')
    plt.ylabel('Time (seconds)')
    plt.title('Time vs N')

    # Add legend and grid
    plt.legend()
    plt.grid(True)

    # Save plot as PNG
    plt.savefig(output_png)

    # Show plot
    plt.show()

if __name__ == "__main__":
    import sys

    if len(sys.argv) != 3:
        print("Usage: python plot_data.py <data_file> <output_png>")
        sys.exit(1)

    data_file = sys.argv[1]
    output_png = sys.argv[2]
    plot_data(data_file, output_png)
