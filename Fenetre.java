import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Fenetre extends JFrame {
    private ArrayList <Territory> Territories = new ArrayList<>();


    public Fenetre(String whatIsItMan, int width, int height) {
        ReadTheFileHarry();
        if (whatIsItMan.equals("map")) {
            this.setTitle("RISK");
            this.setSize(new Dimension(width, height+30));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);

            BufferedImage img = null;
            BufferedImage img2 = null;
            try {
                img = ImageIO.read(new File("map3.jpg"));
                img2 = ImageIO.read(new File("map_Yellow_1125.jpg"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            final BufferedImage img3 = img2;

            JLabel contentPane = new JLabel();
            if (img != null) {
                contentPane.setIcon(new ImageIcon(img));
            }
            contentPane.setLayout(new BorderLayout());
            contentPane.addMouseListener(new MyMouseListener() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    super.mouseClicked(event, img3);
                }
            });

            this.setContentPane(contentPane);
            this.setVisible(true);
        }

    }

    abstract class MyMouseListener implements MouseListener {
        private void mouseClicked(MouseEvent event, BufferedImage img) {
            int x = event.getX();
            int y = event.getY();
            Color color = new Color(img.getRGB(x, y));
            System.out.println(color);
            String nigga = WhatsTerritoryNigga(color);
            //System.out.println(nigga);
        }
        public void mouseEntered(MouseEvent event) {
        }
        public void mouseExited(MouseEvent event) {
        }
        public void mousePressed(MouseEvent event) {
        }
        public void mouseReleased(MouseEvent event) {
        }
    }

    private String WhatsTerritoryNigga(Color color) {
        int blue = color.getBlue();
        for (Territory territory : Territories) {
            for (int l=0; l<5; l++) {
                try {
                    Color color_temp = new Color(255, 255, blue - 2 + l);
                    if (color_temp.equals(territory.getColor())) {
                        System.out.print("Les adjacents de " + territory.getName() + " sont ");
                        for (Territory adjacents : territory.getAdjacents()) {
                            System.out.print(adjacents.getName() + ", ");
                        }
                        System.out.println("");
                        return territory.getName();
                    }
                } catch (IllegalArgumentException iae) {
                    return "";
                }
            }
        }
        return "";

    }

    private void ReadTheFileHarry () {
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("territoires.txt"));  // FileNotFoundException
            while(( currentLine=br.readLine())!= null) {
                String[] line = currentLine.split("/"); // Separate territory from his color
                String country = line[0]; // Name of the territory
                String color_str = line[1]; // Color of the territory
                String[] color_line = color_str.split(","); // Separate the RGB components of the color

                int r = Integer.parseInt(color_line[0]);
                int g = Integer.parseInt(color_line[1]);
                int b = Integer.parseInt(color_line[2]);
                Color color = new Color(r,g,b);

                Territory territory = new Territory(country, color);
                Territories.add(territory);
            }

            for (Territory territory : Territories) {
                setMyMates(territory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Territory getTerritoryByName(String name) {
        Territory territory = Territories.get(0);
        for (Territory territory_temp : Territories) {
            if ((territory_temp.getName()).equals(name)) {
                return territory_temp;
            }
        }
        return territory;
    }

    private void setMyMates(Territory territory) {
        try {
            String currentLine_adj;
            BufferedReader br_adj = new BufferedReader(new FileReader("adjacents.txt"));  // FileNotFoundException

            while (( currentLine_adj=br_adj.readLine()) != null) {
                String[] line_adj = currentLine_adj.split("/"); // Separate territory from adjacents
                if (territory.getName().equals(line_adj[0])) {
                    String[] adjacents = line_adj[1].split(",");
                    for (String adjacent : adjacents) {
                        territory.addAdjacents(getTerritoryByName(adjacent));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}