package be.beme.schn;


//  Ahy - A pure java CMS.
//  Copyright (C) 2010 Sidney Leal (manish.com.br)
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Copyright (C) 2010 Sidney Leal (manish.com.br)
 * */

public final class ImageUtil {


    public static BufferedImage squareCropAndScale(BufferedImage src, int scaleWidth, int scaleHeight) throws IOException {

        BufferedImage dest;

        if (src.getWidth() <= src.getHeight())
        {
            dest = ImageUtil.crop(src, src.getWidth(), src.getWidth());
        }
        else
        {
            dest = ImageUtil.crop(src, src.getHeight(), src.getHeight());
        }

        dest = ImageUtil.scale(dest, scaleWidth, scaleHeight);

        return dest;
    }

    public static BufferedImage crop(BufferedImage src, int width, int height) throws IOException {
        int x = src.getWidth()/2 - width/2;
        int y = src.getHeight()/2 - height/2;


        BufferedImage clipping = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//src.getType());
        Graphics2D area = (Graphics2D) clipping.getGraphics().create();
        area.drawImage(src, 0, 0, clipping.getWidth(), clipping.getHeight(), x, y, x + clipping.getWidth(),
                y + clipping.getHeight(), null);
        area.dispose();

        return clipping;
    }


    public static BufferedImage scale(BufferedImage src, int width, int height) throws IOException {
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(
                (double)width/src.getWidth(),
                (double)height/src.getHeight());
        g.drawRenderedImage(src,at);
        return dest;
    }
}

