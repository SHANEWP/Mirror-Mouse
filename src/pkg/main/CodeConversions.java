package pkg.main;

public class CodeConversions {
    public CodeConversions() {}

    public static int getKeyConversion (int code) {
        switch(code) {
            case 0x000B: //0
                return 48;
            case 0x0002: //1
                return 49;
            case 0x0003: //2
                return 50; 
            case 0x0004: //3
                return 51; 
            case 0x0005: //4
                return 52; 
            case 0x0006: //5
                return 53; 
            case 0x0007: //6
                return 54; 
            case 0x0008: //7
                return 55; 
            case 0x0009: //8
                return 56; 
            case 0x000A: //9
                return 57; 

            case 0x001E: //A
                return 65;
            case 0x0030: //B
                return 66; 
            case 0x002E: //C
                return 67; 
            case 0x0020: //D
                return 68;
            case 0x0012: //E
                return 69; 
            case 0x0021: //F
                return 70; 
            case 0x0022: //G
                return 71;
            case 0x0023: //H
                return 72; 
            case 0x0017: //I
                return 73;
            case 0x0024: //J
                return 74; 
            case 0x0025: //K
                return 75; 
            case 0x0026: //L
                return 76;
            case 0x0032: //M
                return 77;
            case 0x0031: //N
                return 78; 
            case 0x0018: //O
                return 79; 
            case 0x0019: //P
                return 80; 
            case 0x0010: //Q
                return 81; 
            case 0x0013: //R
                return 82; 
            case 0x001F: //S
                return 83; 
            case 0x0014: //T
                return 84;
            case 0x0016: //U
                return 85;
            case 0x002F: //V
                return 86; 
            case 0x0011: //W
                return 87;
            case 0x002D: //X
                return 88; 
            case 0x0015: //Y
                return 89;
            case 0x002C: //Z
                return 90; 

            case 0x0039: //SPACE
                return 32;
            default: //SHIFT 
                return 16;
        }
    }

    public static int getMouseConversion(int code) {
        switch (code) {
            case 1: 
                return 1;
            case 2: 
                return 3;
            default:
                return 2;
        }
    }
}
