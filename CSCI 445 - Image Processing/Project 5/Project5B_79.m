function [ACC, F1] = Project5B_79(G, BW)

    %Turn G and BW into single column arrays. Then use logical operand 
    % '&' to compare the arrays to calculate TP, FP, TN, FN.
    G = imbinarize(G);
    TP = sum(G(:) & BW(:)); 

    FP = sum(~G(:) & BW(:));

    TN = sum(~G(:) & ~BW(:));

    FN = sum(G(:) & ~BW(:));

    ACC = (TP + TN) / (TP + TN + FP + FN);

    F1 = (2 * TP) / ((2 * TP) + FP + FN);
end 
