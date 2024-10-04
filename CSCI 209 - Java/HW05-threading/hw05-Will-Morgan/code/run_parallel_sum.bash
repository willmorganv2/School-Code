# Check if 6 arguments are passed
if [ "$#" -ne 6 ]; then
  echo "Usage: $0 arrayStart arrayEnd arrayStep threadStart threadEnd threadStep"
  exit 1
fi

# Assign command line arguments to variables
arrayStart=$1
arrayEnd=$2
arrayStep=$3
threadStart=$4
threadEnd=$5
threadStep=$6

# Compile Java program (assuming ParallelSum.java is in the current directory)
javac ParallelSum.java

# Outer loop for array size
for (( arraySize=$arrayStart; arraySize<=$arrayEnd; arraySize*=$arrayStep ))
do
  # Inner loop for number of threads
  for (( numThreads=$threadStart; numThreads<=$threadEnd; numThreads+=$threadStep ))
  do
    echo "Running with array size $arraySize and $numThreads threads..."
    java ParallelSum $numThreads $arraySize "N_P_T_1_T_P_S_P_E_P.txt"
  done
  echo "--------------------"
done

