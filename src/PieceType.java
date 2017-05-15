import java.awt.Color;

public enum PieceType {
        LINE (1),
        TBLOCK (2),
        SQUARE (3),
        LBLOCK (4),
        RLBLOCK (5),
        SBLOCK (6),
        RSBLOCK (7);

        private final int type;

        PieceType(int type){
            this.type = type;
        }

        public Color color(){
            switch(type){
                case 1:
                break;
                case 2:
                break;
                case default:
                break;
            }

            return new Color(0, 0, 0);
        }
}