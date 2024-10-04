function [g, h] = Project1_79(f)
%This function takes in a gray scale image and applies a transformation to
% it that enhances the image. This function creates a one dimensional array
% and then applies an exponential transformation that makes a histogram.
% After that the function uses histeq to match the original picture to the
% given histogram. 

f = im2double(f);

% create a one dimensional array with values 0 to 1
%use 256 so that the array will account for gray scale images
x = linspace(0,1,256);

% create a histogram function using the array
% the first part of the equation accounts for the very large amount of
% black in the image, using e^-10x creates a curve that is exponential
% but the curve also goes down toward the gray areas. 18x creates spacing
% so that the curve does not end early or too late and  
% placed in the correct spot along the x axis. 
% The addition of the second part of the equation is necessary because 
% without it the line flattens out. e^-10(1-x) creates a small bump to  
% account for the white in the image and 2(1-x) will control
% placement of the curve.
h = 18*x.*exp(-10*x) + 2*(1-x).*exp(-10*(1-x));  

% match the image to the function created.    
g = histeq(f,h);
end