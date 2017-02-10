package autentica;

import java.util.InputMismatchException;

public class ValidaCPF {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean eValido(String cpf) {
        try {
            for (int i=0; i<cpf.length(); i++){
                int aux = (int)(cpf.charAt(i)-48);
            }
        } catch (InputMismatchException erro){
            return false;
        }
        if (cpf == null || cpf.length() != 11 || cpf.matches("^(0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|9{11}|9{11})$")) return false;
        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }
}
