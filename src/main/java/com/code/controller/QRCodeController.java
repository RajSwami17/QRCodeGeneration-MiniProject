package com.code.controller;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.code.entity.QRCodeDetails;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Controller
@RequestMapping("/generate")
public class QRCodeController 
{
	
	@ModelAttribute("generate")
	public QRCodeDetails qrCodeDetails()
	{
		return new QRCodeDetails();
	}
	
	@GetMapping
	public String QRDetails()
	{
		return "qrDetails";
	}
	
	@PostMapping
	public String generateQRCode(@ModelAttribute("generate")QRCodeDetails qrCodeDetails,Model model)
	{
		try {
			BufferedImage bufferedImage = generateQRCodeImage(qrCodeDetails);
			File outputFile = new File("D:\\QRCode\\"+qrCodeDetails.getFirstName()+ "_" + qrCodeDetails.getLastName()+".jpg");
			ImageIO.write(bufferedImage,"jpg", outputFile);
			model.addAttribute("generate",qrCodeDetails);
		}
		catch(Exception e) {
			e.getMessage();
		}
		return "redirect:/generate?success";
	}

	public static BufferedImage generateQRCodeImage(QRCodeDetails qrCodeDetails) throws Exception 
	{
		StringBuilder str = new StringBuilder();
		str = str.append("FirstName:").append(qrCodeDetails.getFirstName())
				.append(" || ").append("LastName:").append(qrCodeDetails.getLastName()).append(" || ")
				.append("Email:").append(qrCodeDetails.getEmail()).append(" || ").append("MobileNo:")
				.append(qrCodeDetails.getMobileNo()).append(" || ").append("Address:").append(qrCodeDetails.getAddress());
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(str.toString(), BarcodeFormat.QR_CODE, 300, 300);		
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	
}
