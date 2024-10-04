function [BW, ratio, count] = Project3_79(f)
    % Convert image to grayscale if it's not already in grayscale
    if size(f, 3) == 3
        f_gray = im2gray(f);
    else
        f_gray = f;
    end

    % Thresholding to obtain binary image
    level = graythresh(f_gray);
    BW = imbinarize(f_gray, level);

    % Apply morphological operations to clean up the binary image
    se = strel('disk', 3);
    BW = imopen(BW, se);
    BW = imclose(BW, se);

    % Fill holes in the binary image
    BW_filled = imfill(BW, 'holes');

    % Calculate area occupied by bubbles
    bubble_area = sum(BW_filled(:));

    % Calculate total area of the image
    total_area = numel(BW_filled);

    % Calculate ratio of area occupied by bubbles to total area
    ratio = bubble_area / total_area;

    % Find connected components to count distinct bubbles
    CC = bwconncomp(BW_filled);
    count = CC.NumObjects;
end
