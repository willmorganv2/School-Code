function [g] = Project2_79(f)
%Suppresses specific frequency components in an image using Fourier domain
%manipulation. Given image f, this function supresses a certain point of
%frequencies then outputs g (the image with the filter applied)
    F = fftshift(fft2(double(f)));
    mask = ones(size(F));

    mask(476, 583) = 0;
    mask(526, 633) = 0;

    F_suppressed = F .* mask;

    g = ifft2(ifftshift(F_suppressed));
    g = real(g);
    g = uint8(g);
end