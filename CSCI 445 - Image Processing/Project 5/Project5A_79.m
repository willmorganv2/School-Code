function [BW] = Project5A_79(f)
    % Convert the image to grayscale if it's not already
    if size(f, 3) == 3
        f_gray = rgb2gray(f);
    else
        f_gray = f;
    end

    % Binarize the image using automatic thresholding
    bin_image = imbinarize(f_gray);

    % Edge Detection using Canny
    edge_image = edge(f_gray, 'canny');
    W = edge_image;
    % Apply morphological closing to improve connectivity between edges
    se_close = strel('sphere', 3); % Adjust the size of the structuring element as needed
    edge_image = imclose(edge_image, se_close);
    W = imclose(W, se_close);
    W = imfill(W, 'holes');
    W = imerode(W, se_close);
    W = imclose(W, se_close);
    edge_image = edge_image | ~bin_image;
    edge_image = W & edge_image;
    edge_image = imclose(edge_image, se_close);
    edge_image = imopen(edge_image, se_close);
    edge_image = imerode(edge_image, se_close);
    edge_image = imdilate(edge_image, se_close);

    % Segment lungs from the rest of the image
    lung_mask = edge_image > graythresh(f_gray); % Thresholding
    se = strel('sphere', 2); % Adjust the size of the structuring element as needed
    lung_mask = imclose(lung_mask, se); % Morphological opening
    lung_mask = imfill(lung_mask, 'holes'); % Fill holes
    lung_mask = imopen(lung_mask, se); % Morphological opening
    lung_mask = imdilate(lung_mask, se);
    % Morphological operations on lung regions
    se = strel('sphere', 3);
    lung_mask = imclose(lung_mask, se);
    lung_mask = imopen(lung_mask, se);
    BW = imfill(lung_mask, 'holes');
    BW = imerode(BW, se);
    BW = imopen(BW, se);

    cc = bwconncomp(BW);
    numPixels = cellfun(@numel, cc.PixelIdxList);
    [~, idx] = sort(numPixels, 'descend');
    for i = 3:length(idx)
        BW(cc.PixelIdxList{idx(i)}) = 0; % Set pixels of smaller masses to 0
    end

    BW = imdilate(BW, se);
end