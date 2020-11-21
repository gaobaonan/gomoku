//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class BoradMouseListener extends MouseAdapter {
//    @Override
//    public void mousePressed(MouseEvent e) {
//        if(playable) {
//            cc.refresh();
//            int x = e.getX();
//            int y = e.getY();
//            //判断点击是否在棋盘内
//            if (x >= 15 && x < 465 && y >= 55 && y < 505) {
//                x = (x - 15) / 30;
//                y = (y - 55) / 30;
//
//                if (cc.getChess()[x][y] == 0) {
//                    System.out.println("X=" + e.getX() + " Y=" + e.getY());
//                    if (turn) cc.getChess()[x][y] = 1;
//                    else cc.getChess()[x][y] = 2;
//                    turn = !turn;
//                }
//            }
//            this.repaint();
//            win();
//
//            if(gamemode == 0){
//                playable = false;
//                cc.step();
//                turn = !turn;
//                playable = true;
//                win();
//            }
//
//        }
//    }
//}
