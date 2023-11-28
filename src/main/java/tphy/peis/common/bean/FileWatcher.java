package tphy.peis.common.bean;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

/**
 * @ClassName: FileWatcher
 * @Description: //TODO
 * @Date: 2023/11/7 15:36
 * @Author: ZCZ
 **/
@Component
public class FileWatcher {
    private final PdfToImageService pdfToImageService;

    public FileWatcher(PdfToImageService pdfToImageService) {
        this.pdfToImageService = pdfToImageService;
    }

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void processPdfFiles() {
        String pdfFolderPath = "Z:\\pic";
        pdfToImageService.convertPdfToImage(pdfFolderPath);
    }

    public void startFileWatcher(String pdfFolderPath) throws IOException {
        Path path = Paths.get(pdfFolderPath);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        new Thread(() -> {
            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        // 有新的文件添加，执行处理方法
                        pdfToImageService.convertPdfToImage(pdfFolderPath);
                    }
                }
                key.reset();
            }
        }).start();
    }
}