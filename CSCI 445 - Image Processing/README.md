# The final project we worked on in CSCI 445 - Image processing that combined all previous methods and techiques involved in image processing and analysis.
# Project 5: Lung Segmentation from X-ray Images

## Overview
The goal of this project was to develop a MATLAB function that extracts lungs from x-ray images using various image processing techniques. The final product is a binary (black and white) image where the lungs are highlighted in white.

## Dataset
- 20 x-ray images
- 20 ground truth images for validation

## Project Process
1. **Edge Detection**: Detected all edges in the x-ray images.
2. **Gap Filling**: Applied morphological operations to fill gaps and produce more coherent shapes.
3. **Inversion & Logical Operations**: Compared the inverse of the x-ray image with the edge image using a logical AND operator to enhance lung regions.
4. **Masking & Morphological Processing**: Applied masking techniques and morphological operations to further isolate the lungs.
5. **Final Segmentation**: Identified the two largest coherent shapes in the image, corresponding to the lungs.

## File
- **Project5A_79.m**: MATLAB script that implements the lung segmentation process.

## Learning Outcomes
Through this project, I gained experience in:
- Medical image analysis using MATLAB
- Applying advanced image processing techniques
- Developing segmentation algorithms for real-world datasets
