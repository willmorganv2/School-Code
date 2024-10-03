function [BW, ratio, count] = Project4A_79(f)
%Project 4A 
%   This function uses Otsu's method to spot any multiple of objects from
%   the background based on intensities. Then the function applies
%   morphological operations to the BW image to smoothen the objects, close
%   any gaps, and remove noises. The count of the distinct objects are
%   received back and as well as the ratio of them in the image.

    f_gray = im2gray(f);
    
    value = graythresh(f_gray); %Slides, 3D
    BW = im2bw(f_gray, value); 

    disk = strel('disk', 6); % Slides 3B
    BW = imclose(BW, disk); % Slides, 3B
    BW = imopen(BW, disk); % Slides, 3B

    BW_fill = imfill(BW, 'holes');

    bubble_area = sum(BW_fill(:));

    total_area = numel(BW_fill);

    ratio = bubble_area / total_area;

    [L, num] = bwlabel(BW_fill); %Slides, 3B
    count = num;
end