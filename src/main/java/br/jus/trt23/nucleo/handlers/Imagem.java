package br.jus.trt23.nucleo.handlers;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Imagem
{

	/**
	 * Redimensiona uma imagem, tendo como entrada um array de bytes.
	 * Retorna uma imagem reduzida em relação à original, ou uma imagem de mesmo tamanho
	 * 
	 * @param imagemOriginal: Imagem a redimensionar
	 * @param largura: A largura desejada. A altura será prorcional, de acordo com as dimensões da imagem original
	 * @param formatoImagem: Designação informal do formato de arquivo de entrada/saída
	 * @return Imagem original reduzida, ou no mesmo tamanho
	 */
	public static byte[] redimensionaImagem(byte[] imagemOriginal, int largura, String formatoImagem) throws IOException {
		ByteArrayInputStream inputStream;                 // stream para ler bytes da imagem original
		ByteArrayOutputStream outputStream;               // stream para ler bytes imagem resultante
		BufferedImage bImagemOriginal, bImagemResultante; // imagens com buffer de acesso
		Graphics2D g;                                     // objeto para renderização de imagens 
		AffineTransform at;                               // mapeamento linear de coordenadas 2D - usado em transformações de imagens
		int larguraOriginal, alturaOriginal, altura;      // largura e altura da imagem original e resultante
		float pLargura, pAltura;                          // proporção do redimensionamento da imagem original
		
		// Lê a array de bytes original e transforma-o em uma BufferedImage
		inputStream = new ByteArrayInputStream(imagemOriginal);
    	bImagemOriginal = ImageIO.read(inputStream);
    	
    	// Lê largura e altura originais da imagem usando a BufferedImage
    	larguraOriginal = bImagemOriginal.getWidth();
    	alturaOriginal = bImagemOriginal.getHeight();
    	
    	// Se a largura desejada for menor que a largura original, 
    	// faz o redimensionamento. Caso negativo, retorna imagem original
    	if (largura < larguraOriginal) {
    		// calcula proporção baseada na largura desejada
	    	altura = (largura * alturaOriginal)/larguraOriginal;
	    	pLargura = (float)largura/larguraOriginal;
	    	pAltura = (float)altura/alturaOriginal;
	    	
	    	// Cria uma bufferedImage com as dimensões desejadas e tipo igual ao da imagem original
	    	bImagemResultante = new BufferedImage(largura, altura, bImagemOriginal.getType());
	    	// redimensiona imagem de acordo com as proporções calculadas
	    	g = bImagemResultante.createGraphics();
	    	at = AffineTransform.getScaleInstance(pLargura, pAltura);
	        g.drawRenderedImage(bImagemOriginal, at);
	        
	        // converte a bufferedImage resultante em um array de bytes
	        outputStream = new ByteArrayOutputStream();
	        ImageIO.write(bImagemResultante, formatoImagem, outputStream);
	        outputStream.flush();
	        byte[] imagemRetorno = outputStream.toByteArray();
	        outputStream.close();
	        
	        // retorna array de bytes da imagem convertida
	        return imagemRetorno;
    	}
    	else {
    		// retorna array de bytes da imagem original
    		return imagemOriginal;
    	}
	}

}