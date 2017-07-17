package com.diyiliu;

import sun.nio.cs.ext.SJIS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Description: MainFrame
 * Author: DIYILIU
 * Update: 2017-07-14 15:47
 */
public class MainFrame extends JFrame implements ActionListener {

    private JPanel pnHeader;

    private JScrollPane splContent;

    private JButton btOpen, btExecute;

    private JTextField tfPath, tfSuffix, tfOld, tfNew;

    private JLabel lbSuffix, lbOld, lbNew;

    private JTextArea taContent;

    private File[] files;

    public MainFrame() {
        try {
            // 设置样式
            String ui = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(ui);

        } catch (Exception e) {
            e.printStackTrace();
        }

        btOpen = new JButton("打开");
        btExecute = new JButton("执行");

        lbSuffix = new JLabel(" 类型");
        lbOld = new JLabel("原命名");
        lbNew = new JLabel("新命名");

        tfPath = new JTextField();
        tfPath.setEnabled(false);
        tfSuffix = new JTextField();
        tfOld = new JTextField();
        tfNew = new JTextField();

        taContent = new JTextArea();

        pnHeader = new JPanel();
        GroupLayout layout = new GroupLayout(pnHeader);
        pnHeader.setLayout(layout);

        //自动设定组件、组之间的间隙
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // 水平分组
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(tfPath)
                .addGroup(layout.createSequentialGroup().addComponent(lbOld).addComponent(tfOld).addComponent(lbNew).addComponent(tfNew)
                        .addComponent(lbSuffix).addComponent(tfSuffix)));

        hGroup.addGroup(layout.createParallelGroup().addComponent(btOpen).addComponent(btExecute));

        layout.setHorizontalGroup(hGroup);

        // 垂直分组
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(tfPath).addComponent(btOpen));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lbOld)
                .addComponent(tfOld)
                .addComponent(lbNew)
                .addComponent(tfNew)
                .addComponent(lbSuffix)
                .addComponent(tfSuffix)
                .addComponent(btExecute));
        layout.setVerticalGroup(vGroup);

        splContent = new JScrollPane();
        splContent.setViewportView(taContent);

        this.add(pnHeader, BorderLayout.NORTH);
        this.add(splContent);

        // 添加监听
        btOpen.addActionListener(this);
        btOpen.setActionCommand("open");

        btExecute.addActionListener(this);
        btExecute.setActionCommand("execute");


        this.setSize(500, 400);
        //设置窗口居中
        int WIDTH = this.getWidth();
        int HEIGHT = this.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screamSize = kit.getScreenSize();
        this.setBounds((screamSize.width - WIDTH) / 2, (screamSize.height - HEIGHT) / 2, WIDTH, HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public static void main(String[] args) {

        new MainFrame();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("open")) {

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("选择文件夹");
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.showOpenDialog(this);

            String path = chooser.getSelectedFile().getAbsolutePath();
            tfPath.setText(path);

            File dir = new File(path);
            files = dir.listFiles();
        } else if (e.getActionCommand().equals("execute")) {
            if (files != null && files.length > 0) {

                rename(files);
            }
        }
    }


    public void rename(File[] files) {
        String suffix = tfSuffix.getText().trim();
        for (File f : files) {
            if (f.isDirectory()){
                rename(f.listFiles());
            }

            if (f.getName().endsWith(suffix) || suffix.length() < 1) {
                if (f.getName().contains(tfOld.getText())) {

                    String old = tfOld.getText();
                    if (old.contains("[")){
                        old = old.replaceAll("\\[", "\\\\[");
                    }

                    String str = f.getParent() + File.separator + f.getName().replaceAll(old, tfNew.getText());
                    File newFile = new File(str);
                    if (newFile.exists()) {
                        String p = isExists(newFile, "_1");
                        newFile = new File(p);
                    }
                    if (f.renameTo(newFile)) {
                        taContent.append(newFile.getAbsolutePath() + "\r\n");
                    }
                }
            }
        }
    }

    public String isExists(File file, String attach) {

        String path = file.getAbsolutePath();
        if (file.exists()) {
            if (file.isFile()) {
                String[] array = file.getName().split("\\.");
                path = file.getParent() + File.separator +  array[0] + attach + "." +  array[1];
            } else {
                path = file.getAbsolutePath() + attach;
            }
           return isExists(new File(path), attach);
        } else {

            return path;
        }
    }
}
