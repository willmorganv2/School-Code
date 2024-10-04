import matplotlib.pyplot as plt

def plot_data(results_file, outfile):
    N, T_W, T_O, DP = [], [], [], []

    with open(results_file, 'r') as f:
        next(f)  # Skip header
        for line in f:
            data = line.split()
            N.append(int(data[0]))
            T_W.append(float(data[1]))
            T_O.append(float(data[2]))
            DP.append(int(data[3]))

    plt.figure(figsize=(10, 6))
    plt.plot(N, T_W, label='Total Time (T_W)')
    plt.plot(N, T_O, label='Loop Time (T_O)')
    plt.xlabel('Problem Size (N)')
    plt.ylabel('Time (seconds)')
    plt.title('Timing Analysis')
    plt.legend()
    plt.grid(True)
    
    # Format y-axis ticks in scientific notation
    plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0))
    
    # Show the plot
    plt.tight_layout()
    plt.savefig(outfile)
    plt.show()

if __name__ == "__main__":
    import sys

    if len(sys.argv) != 3:
        print("Usage: python plot_data.py <results.txt> <outfile.png>")
        sys.exit(1)

    results_file = sys.argv[1]
    outfile = sys.argv[2]

    plot_data(results_file, outfile)
