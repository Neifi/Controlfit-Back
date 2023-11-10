package es.neifi.controlfit.customer.ficheros.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import es.neifi.controlfit.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import es.neifi.controlfit.customer.storage.ApiError;
import es.neifi.controlfit.customer.storage.StorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor

public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	
	private final StorageService storageService;
	
	@ApiOperation(value = "Obtener imagen fichero subido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Customer.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	
	@GetMapping(value="/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
		Resource file = storageService.loadAsResource(filename);
		
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("NO se pudo determinar el tipo de archivo.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.body(file);
	}

}