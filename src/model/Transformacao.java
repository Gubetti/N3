package model;

// Referencia da organizacao interna da matriz
//					[ matrix[0] matrix[4] matrix[8]  matrix[12] ]
// Transformacao =  [ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//					[ matrix[2] matrix[6] matrix[10] matrix[14] ]
//					[ matrix[3] matrix[7] matrix[11] matrix[15] ]

/**
 * Classe que realiza as transformações na matriz. CUIDADO AO MUDAR ALGUM MÉTODO EXISTENTE AQUI.
 */
public class Transformacao {
	
	public static final double RAS_DEG_TO_RAD = 0.017453292519943295769236907684886;
	
	private double[] matriz = {	1, 0, 0, 0,
								0, 1, 0, 0,
								0, 0, 1, 0,
								0, 0, 0, 1};
	
	public Transformacao() {
	}
	
	public void FazerIdentidade() {
		for (int i=0; i<16; ++i) {
			matriz[i] = 0.0;
			matriz[0] = matriz[5] = matriz[10] = matriz[15] = 1.0;
		}
	}
	
	public void FazerTranslacao(Ponto vetorTranslacao) {
	    FazerIdentidade();
	    matriz[12] = vetorTranslacao.GetX();
	    matriz[13] = vetorTranslacao.GetY();
	    matriz[14] = vetorTranslacao.GetZ();
	}
	
	public void FazerRotacaoX(double radiano) {
	    FazerIdentidade();
	    matriz[5] =   Math.cos(radiano);
	    matriz[9] =  -Math.sin(radiano);
	    matriz[6] =   Math.sin(radiano);
	    matriz[10] =  Math.cos(radiano);
	}
	
	public void FazerRotacaoY(double radiano) {
	    FazerIdentidade();
	    matriz[0] =   Math.cos(radiano);
	    matriz[8] =   Math.sin(radiano);
	    matriz[2] =  -Math.sin(radiano);
	    matriz[10] =  Math.cos(radiano);
	}
	
	public void FazerRotacaoZ(double radiano) {
	    FazerIdentidade();
	    matriz[0] =  Math.cos(radiano);
	    matriz[4] = -Math.sin(radiano);
	    matriz[1] =  Math.sin(radiano);
	    matriz[5] =  Math.cos(radiano);
	}	
	
	public void FazerEscala(double eX, double eY, double eZ) {
	    FazerIdentidade();
	    matriz[0] =  eX;
	    matriz[5] =  eY;
	    matriz[10] = eZ;
	}
	
	public Ponto transformarPonto(Ponto ponto) {
		Ponto pontoResult = new Ponto(
				matriz[0]*ponto.GetX()  + matriz[4]*ponto.GetY() + matriz[8]*ponto.GetZ()  + matriz[12]*ponto.GetW(),
				matriz[1]*ponto.GetX()  + matriz[5]*ponto.GetY() + matriz[9]*ponto.GetZ()  + matriz[13]*ponto.GetW(),
				matriz[2]*ponto.GetX()  + matriz[6]*ponto.GetY() + matriz[10]*ponto.GetZ() + matriz[14]*ponto.GetW(),
                matriz[3]*ponto.GetX()  + matriz[7]*ponto.GetY() + matriz[11]*ponto.GetZ() + matriz[15]*ponto.GetW());
		return pontoResult;
	}
	
	public Transformacao transformarMatriz(Transformacao t) {
		Transformacao result = new Transformacao();
	    for (int i=0; i < 16; ++i) {
	        result.matriz[i] =
	              matriz[i%4]    *t.matriz[i/4*4]  +matriz[(i%4)+4] *t.matriz[i/4*4+1]
	            + matriz[(i%4)+8]*t.matriz[i/4*4+2]+matriz[(i%4)+12]*t.matriz[i/4*4+3];
	    }
		return result;
	}
	
	
	public double getElemento(int index) {
		return matriz[index];
	}
	
	public void setElemento(int index, double valor) {
		matriz[index] = valor;
	}

	public double[] getMatriz() {
		return matriz;	
	}
	
	public void setMatriz(double[] novaMatriz) {
	    for (int i = 0; i < 16; i++) {
	        matriz[i] = (novaMatriz[i]);
	    }
	}
	
}
