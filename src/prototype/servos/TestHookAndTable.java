/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.servos;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.hirt.pi.adafruit.pwm.PWMDevice;
import se.hirt.pi.adafruit.pwm.PWMDevice.PWMChannel;

/**
 *
 * @author pi
 */
public class TestHookAndTable extends javax.swing.JFrame {

    PWMChannel tableServo;
    PWMChannel leftServo;
    PWMChannel rightServo;

    private final int tableStartPosition = 150;
    private final int leftStartPosition = 150;
    private final int rightStartPosition = 150;
    private final int upPosition = 90;
    private final int leftLead = 20;
    private final int rightLead = -90;
    private final int width = 290;

    private int tablePwm;
    private int leftPwm;
    private int rightPwm;

    private int x;
    private int y;

    private void setTablePwm(int value) throws IOException {
        tableServo.setPWM(0, value);
        tableSliderLbl.setText(value + "");
        tablePwm = value;
    }

    private void setLeftPwm(int value) throws IOException {
        leftServo.setPWM(0, value);
        leftSliderLbl.setText(value + "");
        leftPwm = value;
    }

    private void setRightPwm(int value) throws IOException {
        rightServo.setPWM(0, value);
        rightSliderLbl.setText(value + "");
        rightPwm = value;
    }

    /**
     * Creates new form TestHookAndTable
     *
     * @throws java.io.IOException
     */
    public TestHookAndTable() throws IOException {
        PWMDevice device = new PWMDevice();
        device.setPWMFreqency(50);
        tableServo = device.getChannel(0);
        tableServo.setPWM(0, tableStartPosition);

        device = new PWMDevice();
        device.setPWMFreqency(50);
        leftServo = device.getChannel(1);
        leftServo.setPWM(0, leftStartPosition);

        device = new PWMDevice();
        device.setPWMFreqency(50);
        rightServo = device.getChannel(2);
        rightServo.setPWM(0, rightStartPosition);

        initComponents();

        tableSlider.setMinimum(0);
        tableSlider.setMaximum(2500);
        leftSlider.setMinimum(45);
        leftSlider.setMaximum(400);
        rightSlider.setMinimum(20);
        rightSlider.setMaximum(430);

        x = 145;
        y = upPosition;

        moveX(0);

        tableSlider.setEnabled(true);
        leftSlider.setEnabled(true);
        rightSlider.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        upBtn = new javax.swing.JButton();
        emergencyBtn = new javax.swing.JButton();
        leftBtn = new javax.swing.JButton();
        rightBtn = new javax.swing.JButton();
        downBtn = new javax.swing.JButton();
        leftSlider = new javax.swing.JSlider();
        tableSlider = new javax.swing.JSlider();
        rightSlider = new javax.swing.JSlider();
        fowardBtn = new javax.swing.JButton();
        backwardBtn = new javax.swing.JButton();
        tableSliderLbl = new javax.swing.JLabel();
        leftSliderLbl = new javax.swing.JLabel();
        rightSliderLbl = new javax.swing.JLabel();
        xLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        yLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        upBtn.setText("Up");
        upBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upBtnActionPerformed(evt);
            }
        });

        emergencyBtn.setText("Emergency");
        emergencyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emergencyBtnActionPerformed(evt);
            }
        });

        leftBtn.setText("Left");
        leftBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftBtnActionPerformed(evt);
            }
        });

        rightBtn.setText("Right");
        rightBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightBtnActionPerformed(evt);
            }
        });

        downBtn.setText("Down");
        downBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downBtnActionPerformed(evt);
            }
        });

        leftSlider.setPaintLabels(true);
        leftSlider.setEnabled(false);
        leftSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                leftSliderStateChanged(evt);
            }
        });

        tableSlider.setPaintLabels(true);
        tableSlider.setEnabled(false);
        tableSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tableSliderStateChanged(evt);
            }
        });

        rightSlider.setPaintLabels(true);
        rightSlider.setEnabled(false);
        rightSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rightSliderStateChanged(evt);
            }
        });

        fowardBtn.setText("Forward");

        backwardBtn.setText("Backward");

        tableSliderLbl.setText("50");

        leftSliderLbl.setText("50");

        rightSliderLbl.setText("50");

        xLbl.setText("jLabel1");

        jLabel1.setText("X=");

        jLabel2.setText("Y=");

        yLbl.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(leftSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rightSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(leftBtn)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(upBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(emergencyBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(rightBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                        .addComponent(fowardBtn)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(downBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backwardBtn)
                        .addGap(42, 42, 42))))
            .addGroup(layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(tableSliderLbl)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(leftSliderLbl)
                .addGap(222, 222, 222))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(rightSliderLbl))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xLbl)
                        .addGap(127, 127, 127)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yLbl)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(leftBtn)
                        .addGap(79, 79, 79))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(upBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tableSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(tableSliderLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leftSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(leftSliderLbl)
                                .addGap(2, 2, 2)
                                .addComponent(rightSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightSliderLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(emergencyBtn)
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rightBtn)
                                    .addComponent(fowardBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(downBtn)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(backwardBtn)))
                        .addGap(35, 35, 35)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xLbl)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(yLbl))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void upBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upBtnActionPerformed
        y -= 5;
        moveX(0);
    }//GEN-LAST:event_upBtnActionPerformed

    private void leftBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftBtnActionPerformed
        moveX(-5);
    }//GEN-LAST:event_leftBtnActionPerformed

    private void rightBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightBtnActionPerformed
        moveX(5);
    }//GEN-LAST:event_rightBtnActionPerformed

    private void moveX(int distance) {
        try {
            x += distance;
            xLbl.setText(x + "");
            yLbl.setText(y + "");
            double ld = Math.sqrt(Math.pow((double) x, 2.0) + Math.pow(y, 2));
            double rd = Math.sqrt(Math.pow((double) (width - x), 2.0) + Math.pow(y, 2));

            setLeftPwm((int) ld - leftLead);
            setRightPwm(290 - ((int) rd + rightLead));
            // TODO add your handling code here:
        } catch (IOException ex) {
            Logger.getLogger(TestHookAndTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void downBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downBtnActionPerformed
         y += 5;
        moveX(0);
    }//GEN-LAST:event_downBtnActionPerformed

    private void tableSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tableSliderStateChanged
        try {
            setTablePwm(tableSlider.getValue());
        } catch (IOException ex) {
            Logger.getLogger(TestHookAndTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableSliderStateChanged

    private void leftSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_leftSliderStateChanged
        try {
            setLeftPwm(leftSlider.getValue());
        } catch (IOException ex) {
            Logger.getLogger(TestHookAndTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_leftSliderStateChanged

    private void rightSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rightSliderStateChanged
        try {
            setRightPwm(rightSlider.getValue());
        } catch (IOException ex) {
            Logger.getLogger(TestHookAndTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_rightSliderStateChanged

    private void emergencyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emergencyBtnActionPerformed
        try {
            setLeftPwm(leftStartPosition);
            setRightPwm(rightStartPosition);
        } catch (IOException ex) {
            Logger.getLogger(TestHookAndTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_emergencyBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestHookAndTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TestHookAndTable().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(TestHookAndTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backwardBtn;
    private javax.swing.JButton downBtn;
    private javax.swing.JButton emergencyBtn;
    private javax.swing.JButton fowardBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton leftBtn;
    private javax.swing.JSlider leftSlider;
    private javax.swing.JLabel leftSliderLbl;
    private javax.swing.JButton rightBtn;
    private javax.swing.JSlider rightSlider;
    private javax.swing.JLabel rightSliderLbl;
    private javax.swing.JSlider tableSlider;
    private javax.swing.JLabel tableSliderLbl;
    private javax.swing.JButton upBtn;
    private javax.swing.JLabel xLbl;
    private javax.swing.JLabel yLbl;
    // End of variables declaration//GEN-END:variables
}
