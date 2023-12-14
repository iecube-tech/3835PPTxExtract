package com.iecube.pptxsplit.model.pptxsplit.service.Impl;

import com.iecube.pptxsplit.baseservice.ex.FileCreateFailedException;
import com.iecube.pptxsplit.baseservice.ex.FileEmptyException;
import com.iecube.pptxsplit.baseservice.ex.InsertException;
import com.iecube.pptxsplit.model.pptxsplit.dto.PPTSplitDto;
import com.iecube.pptxsplit.model.pptxsplit.entity.Pdf;
import com.iecube.pptxsplit.model.pptxsplit.entity.Ppt;
import com.iecube.pptxsplit.model.pptxsplit.mapper.PPTMapper;
import com.iecube.pptxsplit.model.pptxsplit.service.PPTSplitService;
import com.iecube.pptxsplit.model.resource.entity.Resource;
import com.iecube.pptxsplit.model.resource.mapper.ResourceMapper;
import com.spire.presentation.IShape;
import com.spire.presentation.ISlide;
import com.spire.presentation.IVideo;
import com.spire.presentation.Presentation;
import com.spire.presentation.collections.SlideCollection;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PPTSplitServiceImpl implements PPTSplitService {

    @Value("${resource-location}/file/")
    private String filepath;

    @Value("${resource-location}/extract/")
    private String extract;

    @Autowired
    private PPTMapper pptMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    private File target;

    private static final String TYPE_PAGE="page";
    private static final String TYPE_EXTRACT="extract";

    @Override
    public Ppt getById(Integer pptId) {
        Ppt ppt = pptMapper.getById(pptId);
        if(ppt == null){
            throw new FileEmptyException("文件不存在");
        }
        return ppt;
    }

    @Override
    public Ppt uploadPPT(MultipartFile file, Integer courseId) throws IOException {
        if(file == null){
            throw new FileEmptyException("文件为空");
        }
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }

        String fileType = file.getContentType();
        String originFilename = file.getOriginalFilename();
        String filename = this.SavePFile(file);
        Ppt ppt = new Ppt();
        ppt.setCourseId(courseId);
        ppt.setFilename(filename);
        ppt.setOriginFilename(originFilename);
        ppt.setFileType(fileType);
        Integer row = pptMapper.insertPPT(ppt);
        if(row != 1){
            throw new InsertException("插入数据异常");
        }
        return ppt;
    }

    @Override
    public Pdf uploadPDF(MultipartFile file, Integer pptId) throws IOException {
        if(file == null){
            throw new FileEmptyException("文件为空");
        }
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        String fileType = file.getContentType();
        String originFilename = file.getOriginalFilename();
        String filename = this.SavePFile(file);
        Pdf pdf = new Pdf();
        pdf.setPptId(pptId);
        pdf.setFilename(filename);
        pdf.setOriginFilename(originFilename);
        pdf.setFileType(fileType);
        Integer row = pptMapper.insertPDF(pdf);
        if(row != 1){
            throw new InsertException("插入数据异常");
        }
        return pdf;
    }

    @Override
    public List<Resource> splitPPT(Integer pdfId) throws Exception {
        PPTSplitDto pptSplitDto = pptMapper.getPPTSplitDtoByPdfId(pdfId);
        List<Resource> allExtracts = new ArrayList<>();
        List<Resource> pages = PDFtoImage(pptSplitDto);
        List<Resource> images = PPTExtractImage(pptSplitDto);
        List<Resource> videos = PPTExtractVideo(pptSplitDto);
        allExtracts.addAll(pages);
        allExtracts.addAll(images);
        allExtracts.addAll(videos);
        return allExtracts;
    }

    @Override
    public List<Ppt> coursePPTs(Integer courseId) {
        List<Ppt> ppt = pptMapper.coursePPTs(courseId);
        return ppt;
    }

    /**
     * 从pdf文件中提取出图片
     * @param pptSplitDto
     * @return
     */
    private List<Resource> PDFtoImage(PPTSplitDto pptSplitDto){
        String pdfFilename = pptSplitDto.getPdfFilename();
        File pdfFile = new File(filepath+pdfFilename);
        List<Resource> resourceList = new ArrayList<>();
        try{
            // 加载PDF文档
            PDDocument document = PDDocument.load(pdfFile);
            // 创建PDF渲染器
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                // 渲染PDF页面为BufferedImage
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 200); // 200 DPI清晰度，可以根据需求调整
                // 创建course目录
                File parentFile= new File(extract + pptSplitDto.getCourseId());
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                String filename = pptSplitDto.getPptId()+"_page"+(pageIndex + 1) + ".png";
                // 保存图像
                String outputFileName = extract + pptSplitDto.getCourseId()+"/" + filename;
                try{
                    ImageIO.write(image, "png", new File(outputFileName));
                    Resource resource = new Resource();
                    resource.setPptId(pptSplitDto.getPptId());
                    resource.setPage(pageIndex+1);
                    resource.setFilename(filename);
                    resource.setOriginFilename(filename);
                    resource.setType(TYPE_PAGE);
                    resource.setFileType("image/png");
                    Integer row = resourceMapper.insert(resource);
                    if(row!=1){
                        throw new InsertException("插入数据异常");
                    }
                    resourceList.add(resource);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            // 关闭文档
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return resourceList;
    }

    private List<Resource> PPTExtractImage(PPTSplitDto pptSplitDto) throws IOException {
        List<Resource> resourceList = new ArrayList<>();
        String pptFilePath = filepath+pptSplitDto.getPptFilename();
        FileInputStream fis = new FileInputStream(pptFilePath);
        try{
            XMLSlideShow ppt = new XMLSlideShow(fis);
            Dimension pageSize = ppt.getPageSize();
            Double pageWidth=pageSize.getWidth();
            Double pageHeight = pageSize.getHeight();
            List<XSLFSlide> AllPPTList =  ppt.getSlides();
            for(XSLFSlide PPT : AllPPTList){
                List<XSLFShape> PPTShapes = PPT.getShapes();
                int index = 1;
                for(XSLFShape shape:PPTShapes){
                    if(shape instanceof XSLFPictureShape){
                        XSLFPictureShape pictureShape = (XSLFPictureShape) shape;
                        XSLFPictureData picture = pictureShape.getPictureData();
                        // 获取图片的原始字节数组
                        byte[] bytes = picture.getData();
                        String fileType = picture.getContentType();
                        String extension = picture.suggestFileExtension();
                        String size = "x="+shape.getAnchor().getX()+",y="+shape.getAnchor().getY()+",w="+shape.getAnchor().getWidth()
                                +",h="+shape.getAnchor().getHeight()+",pageWidth="+pageWidth+",pageHeight="+pageHeight;
                        String fileName = pptSplitDto.getPptId()+"_page" + PPT.getSlideNumber()+"_image"+index+"_("+size +")."+ extension;
                        String parentPath = extract+pptSplitDto.getCourseId();
                        String outputName = parentPath+"/"+fileName;
                        File parentFile= new File(parentPath);
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }
                        try(FileOutputStream fos = new FileOutputStream(outputName)){
                            fos.write(bytes);
                            Resource resource = new Resource();
                            resource.setPptId(pptSplitDto.getPptId());
                            resource.setPage(PPT.getSlideNumber());
                            resource.setFilename(fileName);
                            resource.setOriginFilename(fileName);
                            resource.setType(TYPE_EXTRACT);
                            resource.setFileType(fileType);
                            resource.setPageWidth(pageWidth);
                            resource.setPageHeight(pageHeight);
                            resource.setX(shape.getAnchor().getX());
                            resource.setY(shape.getAnchor().getY());
                            resource.setWidth(shape.getAnchor().getWidth());
                            resource.setHeight(shape.getAnchor().getHeight());
                            Integer row = resourceMapper.insert(resource);
                            if(row!=1){
                                throw new InsertException("插入数据异常");
                            }
                            resourceList.add(resource);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        index++;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resourceList;
    }

    private List<Resource> PPTExtractVideo(PPTSplitDto pptSplitDto) throws Exception {
        List<Resource> resourceList = new ArrayList<>();
        String pptFilePath = filepath+pptSplitDto.getPptFilename();
        Presentation ppt = new Presentation();
        try{
            ppt.loadFromFile(pptFilePath);
            double pageWidth=ppt.getSlideSize().getSize().getWidth();
            double pageHeight = ppt.getSlideSize().getSize().getHeight();
            SlideCollection iSlides = ppt.getSlides();
            for(int j=0; j<iSlides.size(); j++){
                ISlide slide =iSlides.get(j);
                int index=1;
                for(int i = 0; i< slide.getShapes().getCount(); i++){
                    IShape shape = slide.getShapes().get(i);
                    if(shape instanceof IVideo){
                        IVideo video= (IVideo) shape;
                        double x = video.getLeft();
                        double y = video.getTop();
                        double width=video.getWidth();
                        double height = video.getHeight();
                        try{
                            String fileType =video.getEmbeddedVideoData().getContentType();
                            String[] saveTypes = fileType.split("/");
                            String saveType = saveTypes[saveTypes.length-1];
                            String parentPath = extract+pptSplitDto.getCourseId();
                            String size = "x="+x+",y="+y+",w="+width+",h="+height+",pageWidth="+pageWidth+",pageHeight="+pageHeight;
                            String fileName = pptSplitDto.getPptId()+"_page" + (j+1)+"_video"+index+"_("+size +")."+ saveType;
                            File parentFile= new File(parentPath);
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            video.getEmbeddedVideoData().saveToFile(parentPath+"/"+fileName);
                            Resource resource = new Resource();
                            resource.setPptId(pptSplitDto.getPptId());
                            resource.setPage(j+1);
                            resource.setFilename(fileName);
                            resource.setOriginFilename(fileName);
                            resource.setType(TYPE_EXTRACT);
                            resource.setFileType(fileType);
                            resource.setPageWidth(pageWidth);
                            resource.setPageHeight(pageHeight);
                            resource.setX(x);
                            resource.setY(y);
                            resource.setWidth(width);
                            resource.setHeight(height);
                            Integer row = resourceMapper.insert(resource);
                            if(row!=1){
                                throw new InsertException("插入数据异常");
                            }
                            resourceList.add(resource);
                        }catch (NullPointerException e){
                            List<Exception> exceptionList = new ArrayList<>();
                            exceptionList.add(e);
                            log.error(pptSplitDto.getCourseId()+"/page"+(j+1)+"_video"+index+": 视频文件为链接");
                            log.warn(exceptionList.toString());
                        }
                        index++;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resourceList;
    }

    private String SavePFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        String fileName = UUID.randomUUID().toString().replace("-", "")+"."+suffix;
        this.target = new File(this.filepath, fileName);
        if (!target.exists()) {
            File parentFile = target.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            boolean success = target.createNewFile();
            if (!success){
                throw new FileCreateFailedException("创建文件失败");
            }
        }
        FileCopyUtils.copy(file.getBytes(), target);
        return fileName;
    }
}
