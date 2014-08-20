/** Copyright 2014 Donald Ray Crawford* * This file is part of Oochis EHR.* * Oochis EHR is free software: you can redistribute it and/or modify* it under the terms of the GNU General Public License as published by* the Free Software Foundation, either version 3 of the License, or* (at your option) any later version.* * This program is distributed in the hope that it will be useful,* but WITHOUT ANY WARRANTY; without even the implied warranty of* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the* GNU General Public License for more details.* * You should have received a copy of the GNU General Public License* along with Oochis EHR.  If not, see <http://www.gnu.org/licenses/>.*/package oochisCore;import java.awt.Color;import java.awt.Dimension;import java.awt.Graphics;import java.awt.GridBagConstraints;import java.awt.GridBagLayout;import java.awt.event.MouseEvent;import java.awt.event.MouseListener;import java.util.Vector;import javax.swing.JButton;import javax.swing.JLabel;import javax.swing.JPanel;import javax.swing.border.LineBorder;public class LifeExpectancy2Panels extends JPanel implements MouseListener {JLabel yAxisJLabel = new JLabel();JLabel xAxisJLabel = new JLabel();static int originX = 50;static int originY = 30;static int graphHeight = 260;static int graphWidth = 300;Color lifeExtendedLifeExpectencyLineColor = new Color(0, 128, 128);Color lifeExpectencyColor = new Color(192, 32, 32);Color buttonColor = Color.BLACK;JLabel LifeExtendedLifeExpectencyCurvePointTextJLabel;JLabel standardLifeExpectencyCurvePointTextJLabel;int pointInd;int[] lifeExpectencyCurvePointsX;int[] lifeExpectencyCurvePointsY;Vector<Double> graphPointsY = null;int[] graphPointsYInts;int[] graphPointsXInts;double largestY = 0;double smallestY = 100000;int xStretch = 3;int highestXInd;int lowestXInnerInd;int lowestXInd;double highestPixelY = 0;double lowestPixelY = 100000;double highestPixelX = 0;double lowestPixelX = 100000;int middleX;int ind;JPanel lowestXTicTextOnHorizontalAxisJPanel = new JPanel(new GridBagLayout());JPanel middleXTicTextOnHorizontalAxisJPanel = new JPanel(new GridBagLayout());JPanel highestXTicTextOnHorizontalAxisJPanel = new JPanel(new GridBagLayout());JLabel lowestXTicTextOnHorizontalAxisJLabel = new JLabel();JLabel middleXTicTextOnHorizontalAxisJLabel = new JLabel();JLabel highestXTicTextOnHorizontalAxisJLabel = new JLabel();JLabel lowestYTicTextOnVerticalAxisJLabel = new JLabel();JLabel middleYTicTextOnVerticalAxisJLabel = new JLabel();JLabel highestYTicTextOnVerticalAxisJLabel = new JLabel();JButton dotJButton;JLabel dotButtonTopJLabel;JLabel dotButtonBottomJLabel;GridBagConstraints dotButtonConstraints = new GridBagConstraints();GridBagConstraints xTicTextOnHorizontalAxisPanelConstraints = new GridBagConstraints();LifeExpectancy2Panels(Vector<Vector<Double>> survivalCurvePointsY, String yAxisLabelText, String xAxisLabelText, int startAge) {setLayout(null);setSize(new Dimension(900, 400));setPreferredSize(new Dimension(900, 400));int smallestX = 0;int middlePixelX;int largestX = 0;yAxisJLabel.setText(yAxisLabelText);xAxisJLabel.setText(xAxisLabelText);setBackground(Color.WHITE);yAxisJLabel.setBounds(10, -8, 260, 40);xAxisJLabel.setBounds(188, graphHeight + 50, 100, 40);add(xAxisJLabel);add(yAxisJLabel);if (null != survivalCurvePointsY) {int plotInd = 0;int pointInd;while (plotInd < survivalCurvePointsY.size()) {if (largestX < survivalCurvePointsY.elementAt(plotInd).size()) {largestX = survivalCurvePointsY.elementAt(plotInd).size();}middleX = (int) (survivalCurvePointsY.elementAt(plotInd).size() / 2);largestX = survivalCurvePointsY.elementAt(plotInd).size();pointInd = 0;while (pointInd < survivalCurvePointsY.elementAt(plotInd).size()) {if (largestY < survivalCurvePointsY.elementAt(plotInd).elementAt(pointInd)) {largestY = survivalCurvePointsY.elementAt(plotInd).elementAt(pointInd);}smallestY = 0;pointInd++;}plotInd++;}plotInd = 0;double[] survivalCurvePixelPointsY;while (plotInd < survivalCurvePointsY.size()) {ind = 0;survivalCurvePixelPointsY = new double[survivalCurvePointsY.elementAt(plotInd).size()];while (ind < survivalCurvePixelPointsY.length) {if (yAxisLabelText.equals("Optimized life expectancy") || yAxisLabelText.equals("Life expectancy")) {survivalCurvePixelPointsY[ind] = graphHeight + originY - (survivalCurvePointsY.elementAt(plotInd).elementAt(ind) - smallestY) * (graphHeight / (largestY - smallestY));} else {survivalCurvePixelPointsY[ind] = originY + (survivalCurvePointsY.elementAt(plotInd).elementAt(ind) - smallestY) * (graphHeight / (largestY - smallestY));}if (highestPixelY < survivalCurvePixelPointsY[ind]) {highestPixelY = survivalCurvePixelPointsY[ind];}if (lowestPixelY > survivalCurvePixelPointsY[ind]) {lowestPixelY = survivalCurvePixelPointsY[ind];}if (highestPixelX < xStretch * ind + originX) {highestPixelX = xStretch * ind + originX;highestXInd = ind;}if (lowestPixelX > xStretch * ind + originX) {lowestPixelX = xStretch * ind + originX;lowestXInd = ind;}dotJButton = new JButton();if (yAxisLabelText.equals("Optimized life expectancy") || yAxisLabelText.equals("Life expectancy")) {dotJButton.setText("At age " + String.valueOf(ind) + ", life expectancy is " + String.valueOf(((int) (10 * survivalCurvePointsY.elementAt(plotInd).elementAt(ind))) / 10) + ".");dotJButton.setBounds(originX + xStretch * ind, (int) Math.round(survivalCurvePixelPointsY[ind]), 4, 4);dotJButton.setName("life expectancy or optimized life expectancy");} else {dotButtonTopJLabel = new JLabel("At age " + String.valueOf(ind) + ", the probability of ");String survival = String.valueOf((double) ((Math.round((1 - survivalCurvePointsY.elementAt(plotInd).elementAt(ind)) * 1000)) / 10));dotButtonBottomJLabel = new JLabel("surviving another year is " + survival + ".");dotJButton.setLayout(new GridBagLayout());dotButtonConstraints.gridy = 0;dotJButton.add(dotButtonTopJLabel, dotButtonConstraints);dotButtonConstraints.gridy = 1;dotJButton.add(dotButtonBottomJLabel, dotButtonConstraints);dotJButton.setBounds(originX + xStretch * ind, (int) Math.round(survivalCurvePixelPointsY[ind]), 4, 4);dotJButton.setName("Probability of surviving another year");}if (plotInd == survivalCurvePointsY.size() - 1) {dotJButton.setBorder(new LineBorder(buttonColor, 1));} else {dotJButton.setBorder(new LineBorder(Color.GRAY, 1));}dotJButton.setBackground(Color.WHITE);dotJButton.addMouseListener(this);if (startAge <= ind) {add(dotJButton);}ind++;}plotInd++;}middlePixelX = (int) (((xStretch * highestXInd + xStretch * lowestXInd) / 2) + originX);lowestXTicTextOnHorizontalAxisJLabel.setText(String.valueOf(smallestX));middleXTicTextOnHorizontalAxisJLabel.setText(String.valueOf((largestX - smallestX) / 2));highestXTicTextOnHorizontalAxisJLabel.setText(String.valueOf(largestX));lowestYTicTextOnVerticalAxisJLabel.setText(String.valueOf(((double) (Math.round(10 * smallestY))) / 10));middleYTicTextOnVerticalAxisJLabel.setText(String.valueOf(((double) (Math.round(10 * (largestY + smallestY) / 2))) / 10));highestYTicTextOnVerticalAxisJLabel.setText(String.valueOf(((double) (Math.round(10 * largestY))) / 10));lowestXTicTextOnHorizontalAxisJPanel.setOpaque(false);middleXTicTextOnHorizontalAxisJPanel.setOpaque(false);highestXTicTextOnHorizontalAxisJPanel.setOpaque(false);lowestXTicTextOnHorizontalAxisJPanel.add(lowestXTicTextOnHorizontalAxisJLabel, xTicTextOnHorizontalAxisPanelConstraints);middleXTicTextOnHorizontalAxisJPanel.add(middleXTicTextOnHorizontalAxisJLabel, xTicTextOnHorizontalAxisPanelConstraints);highestXTicTextOnHorizontalAxisJPanel.add(highestXTicTextOnHorizontalAxisJLabel, xTicTextOnHorizontalAxisPanelConstraints);lowestXTicTextOnHorizontalAxisJPanel.setBounds((int) lowestPixelX - 60, graphHeight + originY - 4, 120, 30);middleXTicTextOnHorizontalAxisJPanel.setBounds(middlePixelX - 60, graphHeight + originY - 4, 120, 30);highestXTicTextOnHorizontalAxisJPanel.setBounds((int) highestPixelX - 60, graphHeight + originY - 4, 120, 30);lowestYTicTextOnVerticalAxisJLabel.setBounds(4, (int) highestPixelY - 15, 120, 30);middleYTicTextOnVerticalAxisJLabel.setBounds(4, (int) ((highestPixelY + lowestPixelY) / 2) - 15, 120, 30);highestYTicTextOnVerticalAxisJLabel.setBounds(4, (int) lowestPixelY - 15, 120, 30);add(lowestXTicTextOnHorizontalAxisJPanel);add(middleXTicTextOnHorizontalAxisJPanel);add(highestXTicTextOnHorizontalAxisJPanel);add(lowestYTicTextOnVerticalAxisJLabel);add(middleYTicTextOnVerticalAxisJLabel);add(highestYTicTextOnVerticalAxisJLabel);}}@Overridepublic void paintComponent(Graphics can) {super.paintComponent(can);can.setColor(Color.DARK_GRAY);can.drawLine(originX, originY, originX, graphHeight + originY);can.drawLine(originX + 1, originY, originX + 1, graphHeight + originY);can.drawLine(originX, graphHeight + originY, graphWidth + originX, graphHeight + originY);can.drawLine(originX, graphHeight + originY + 1, graphWidth + originX, graphHeight + originY + 1);}@Overridepublic void mouseClicked(MouseEvent m) {}@Overridepublic void mouseEntered(MouseEvent m) {if (m.getComponent().getName().equals("life expectancy or optimized life expectancy")) {m.getComponent().setBounds(m.getComponent().getX(), m.getComponent().getY() - 30, 245, 30);} else if (m.getComponent().getName().equals("Probability of surviving another year")) {m.getComponent().setBounds(m.getComponent().getX(), m.getComponent().getY() - 40, 250, 40);}m.getComponent().validate();m.getComponent().repaint();}@Overridepublic void mouseExited(MouseEvent m) {if (m.getComponent().getName().equals("life expectancy or optimized life expectancy")) {m.getComponent().setBounds(m.getComponent().getX(), m.getComponent().getY() + 30, 4, 4);} else if (m.getComponent().getName().equals("Probability of surviving another year")) {m.getComponent().setBounds(m.getComponent().getX(), m.getComponent().getY() + 40, 4, 4);}m.getComponent().validate();m.getComponent().repaint();}@Overridepublic void mousePressed(MouseEvent m) {}@Overridepublic void mouseReleased(MouseEvent m) {}}// EOF