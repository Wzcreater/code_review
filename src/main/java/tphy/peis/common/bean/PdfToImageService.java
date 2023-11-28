package tphy.peis.common.bean;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName: PdfToImageService
 * @Description: //TODO
 * @Date: 2023/11/7 15:38
 * @Author: ZCZ
 **/
@Service
public class PdfToImageService {
    public void convertPdfToImage(String pdfFolderPath) {
        File folder = new File(pdfFolderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

        if (files != null) {
            for (File file : files) {
                try {
                    PDDocument document = PDDocument.load(file);
                    PDFRenderer renderer = new PDFRenderer(document);
                    for (int page = 0; page < document.getNumberOfPages(); ++page) {
                        BufferedImage image = renderer.renderImageWithDPI(page, 300); // DPI可以根据需求调整
                        ImageIO.write(image, "jpg", new File(pdfFolderPath + "\\" + file.getName() + "_" + page + ".jpg"));
                    }
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
