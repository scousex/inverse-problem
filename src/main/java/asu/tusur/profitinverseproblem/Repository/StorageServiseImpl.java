package asu.tusur.profitinverseproblem.Repository;

import asu.tusur.profitinverseproblem.exceptions.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class StorageServiseImpl implements StorageService {

    //@Value("${storage.temp-folder}")
    private static String folderLocation = "C:/Web/temp";

    private final Path rootLocation;

    public StorageServiseImpl() {
        rootLocation = Paths.get(folderLocation);
    }

    @Override
    public void init() {
        try{
           Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new StorageException("Невозможно создать директорию",e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(file.isEmpty()){
                throw new StorageException("Ошибка записи: файл "+ fileName +" пуст ");
            }
            if(fileName.contains("..")) {
                throw new StorageException("Ошибка в названии файла. "+fileName);
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, this.rootLocation.resolve(fileName),
                        REPLACE_EXISTING);
            }
        }catch (IOException e){
            throw new StorageException("Ошибка записи файла", e);
        }

    }

    @Override
    public Stream<Path> loadAll() {

        try{
            return Files.walk(rootLocation,1)
                    .filter(path -> path.equals(rootLocation))
                    .map(rootLocation::relativize);
        }catch (IOException e){
            throw new StorageException("Ошибка при загрузке файлов с сервера ",e);
        }

    }

    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try{
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file: " + fileName);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + fileName, e);
        }

    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
