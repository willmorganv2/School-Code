function [ACC, F1] = Project4B_79(G, BW)
%Project4B
%   This function calculates values ACC and F1 based on the ground truth
%   image and the logical image produced by function Project4A_79. ACC is a
%   measurement of how accurate Project4A_79 classifies bubbles and F1 is a
%   measurement of the precision of bubble classification. Basically, its a
%   score on how much bubble Project4A_79 created based compared to the
%   ground truth image.

    %Turn G and BW into single column arrays. Then use logical operand 
    % '&' to compare the arrays to calculate TP, FP, TN, FN.

    TP = sum(G(:) & BW(:)); 
    %True positive - model correctly predicts bubbles. 

    FP = sum(~G(:) & BW(:));
    %False positive - model incorrectly predicts bubbles.

    TN = sum(~G(:) & ~BW(:));
    %True negative - model correctly predicts absence of bubbles.

    FN = sum(G(:) & ~BW(:));
    %False negative - model incorrectly predicts absence of bubbles.

    ACC = (TP + TN) / (TP + TN + FP + FN);

    F1 = (2 * TP) / ((2 * TP) + FP + FN);
end