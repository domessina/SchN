package be.beme.schn.spring.api.v1.controller;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.vaadin.server.VaadinSession;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dotista on 16-08-16.
 */
@RestController
@RequestMapping("/v1/api/")
public class MediaController extends AbstractController {




  /*  @RequestMapping("/nc/{type}/{id}/picture", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getPicture(@PathVariable String ncType,@PathVariable int id) throws IOException {
//        InputStream in = ServletContext.getResourceAsStream("/images/no_image.jpg");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IM);



        return new ResponseEntity<>(new byte[]{12}, headers, HttpStatus.OK);
    }*/
}
