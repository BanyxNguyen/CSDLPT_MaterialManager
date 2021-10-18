/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Services.JobDecisionTree;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

/**
 *
 * @author BenNguyen
 */
public class Main extends javax.swing.JFrame {

    public static Map<String, ContainerMap> Attributes = new Hashtable<>();

    private class ContainerMap {

        public ContainerMap(JComboBox<String> cmb, String[] s) {
            Comb = cmb;
            Value = s;
        }
        public JComboBox<String> Comb;
        public String[] Value;
    }

    /**
     * Creates new form Main2
     */
    public Main(JobDecisionTree tree, JFrame parent) {
        _Tree = tree;
        _Parent = parent;
        initComponents();

        Main.Attributes.put("GT", new ContainerMap(cbGioiTinh, new String[]{"nam", "nu"}));
        Main.Attributes.put("XuHuong", new ContainerMap(cbXuHuong, new String[]{"noi", "ngoai"}));
        Main.Attributes.put("QD", new ContainerMap(cbQDLC, new String[]{"lytri", "tinhcam"}));
        Main.Attributes.put("TanTam", new ContainerMap(cbTanTam, new String[]{"tb", "kha", "cao"}));
        Main.Attributes.put("TuongTac", new ContainerMap(cbTuongTac, new String[]{"tb", "kha", "cao"}));
        Main.Attributes.put("CoiMo", new ContainerMap(cbCoiMo, new String[]{"chuatot", "kha", "tot"}));
        Main.Attributes.put("NangKhieu", new ContainerMap(cbNangKhieu, new String[]{
            "tgg",
            "tuduynhanh",
            "hoatngon",
            "ve",
            "may",
            "cahat",
            "thethao",
            "hhkg"
        }));
        Main.Attributes.put("UocMoCV", new ContainerMap(cbUocMo, new String[]{
            "khoinghiep",
            "htcd",
            "sangtao",
            "tuongtac",
            "nctt",
            "ondinh",
            "ksat",
            "nghethuat"
        }));

        _Parent.setVisible(false);
        System.out.println(_Tree.performTraining());
    }

    private JobDecisionTree _Tree;
    private JFrame _Parent;

    private float AveragePoint(float p0, float p1, float p2) {
        return (p0 + p1 + p2) / 3;
    }

    private float[] GetPointABCD() {
        float[] temps = new float[4];
        temps[0] = ((float) spnToan.getValue() + (float) spnLy.getValue() + (float) spnHoa.getValue()) / 3;
        temps[1] = ((float) spnToan.getValue() + (float) spnHoa.getValue() + (float) spnSinh.getValue()) / 3;
        temps[2] = ((float) spnVan.getValue() + (float) spnSu.getValue() + (float) spnDia.getValue()) / 3;
        temps[3] = ((float) spnToan.getValue() + (float) spnVan.getValue() + (float) spnAnh.getValue()) / 3;
        return temps;
    }

    private float GetPoit(float[] ps, String name) {
        switch (name) {
            case "KhoiA": {
                return ps[0];
            }
            case "KhoiB": {
                return ps[0];
            }
            case "KhoiC": {
                return ps[0];
            }
            case "KhoiD": {
                return ps[0];
            }
            default: {
                return 0;
            }
        }
    }

    private Instance GetTestInstance() {
        Enumeration<Attribute> hh = _Tree.trainingData.enumerateAttributes();
        Instance ii = new DenseInstance(_Tree.trainingData.size() - 1);
        ii.setDataset(_Tree.trainingData);
        int index = 0;

        float[] points = GetPointABCD();

        while (hh.hasMoreElements()) {
            Attribute attr = hh.nextElement();
            String name = attr.name();

            if ("KhoiA".equals(name) || "KhoiB".equals(name) || "KhoiC".equals(name) || "KhoiD".equals(name)) {
                float temp = GetPoit(points, name);
                System.out.println(temp);
                ii.setValue(index++, temp);
            } else {
                ContainerMap conMap = Main.Attributes.get(name);
                Integer idx = conMap.Comb.getSelectedIndex();
                if (idx == 0) {
                    ii.setMissing(index++);
                    System.out.println(ii.value(index - 1));
                } else {
                    System.out.println(conMap.Value[idx - 1]);
                    ii.setValue(index++, conMap.Value[idx - 1]);
                }
            }
        }
        return ii;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbtnGroupKhoi = new javax.swing.ButtonGroup();
        jLabel3 = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox<>();
        lbM1 = new javax.swing.JLabel();
        cbXuHuong = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnRun = new javax.swing.JButton();
        spnToan = new javax.swing.JSpinner();
        lbM2 = new javax.swing.JLabel();
        spnLy = new javax.swing.JSpinner();
        lbM3 = new javax.swing.JLabel();
        spnHoa = new javax.swing.JSpinner();
        lbM4 = new javax.swing.JLabel();
        spnAnh = new javax.swing.JSpinner();
        lbM5 = new javax.swing.JLabel();
        spnSu = new javax.swing.JSpinner();
        lbM6 = new javax.swing.JLabel();
        spnDia = new javax.swing.JSpinner();
        lbM7 = new javax.swing.JLabel();
        spnVan = new javax.swing.JSpinner();
        lbM8 = new javax.swing.JLabel();
        spnSinh = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        cbTanTam = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbCoiMo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbNangKhieu = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbUocMo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbQDLC = new javax.swing.JComboBox<>();
        lbJob = new javax.swing.JLabel();
        cbTuongTac = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Giới tính");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 50));

        cbGioiTinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "nam", "nu" }));
        cbGioiTinh.setPreferredSize(new java.awt.Dimension(100, 50));

        lbM1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM1.setText("Toán");
        lbM1.setPreferredSize(new java.awt.Dimension(100, 50));

        cbXuHuong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbXuHuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "noi", "ngoai" }));
        cbXuHuong.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Xu hướng");
        jLabel5.setToolTipText("abc");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 50));

        btnRun.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRun.setText("Run");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        spnToan.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM2.setText("Lý");
        lbM2.setPreferredSize(new java.awt.Dimension(100, 50));

        spnLy.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM3.setText("Hóa");
        lbM3.setPreferredSize(new java.awt.Dimension(100, 50));

        spnHoa.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM4.setText("Anh");
        lbM4.setPreferredSize(new java.awt.Dimension(100, 50));

        spnAnh.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM5.setText("Sử");
        lbM5.setPreferredSize(new java.awt.Dimension(100, 50));

        spnSu.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM6.setText("Địa");
        lbM6.setPreferredSize(new java.awt.Dimension(100, 50));

        spnDia.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM7.setText("Văn");
        lbM7.setPreferredSize(new java.awt.Dimension(100, 50));

        spnVan.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        lbM8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM8.setText("Sinh");
        lbM8.setPreferredSize(new java.awt.Dimension(100, 50));

        spnSinh.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(1.0f)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Tận tâm");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 50));

        cbTanTam.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbTanTam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "tb", "kha", "cao" }));
        cbTanTam.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Tính cở mở");
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 50));

        cbCoiMo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbCoiMo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "chuatot", "kha", "tot" }));
        cbCoiMo.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Năng khiếu");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 50));

        cbNangKhieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbNangKhieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "tgg", "tuduynhanh", "hoatngon", "ve", "may", "cahat", "thethao", "hhkg" }));
        cbNangKhieu.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Ước mơ");
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 50));

        cbUocMo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbUocMo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "khoinghiep", "htcd", "sangtao", "tuongtac", "nctt", "ondinh", "ksat", "nghethuat" }));
        cbUocMo.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Quyết định");
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 50));

        cbQDLC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbQDLC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "lytri", "tinhcam" }));
        cbQDLC.setPreferredSize(new java.awt.Dimension(100, 50));

        lbJob.setBackground(new java.awt.Color(187, 187, 187));
        lbJob.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbJob.setText("---------- Ngành Nghề ----------");
        lbJob.setAutoscrolls(true);

        cbTuongTac.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbTuongTac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "tb", "kha", "cao" }));
        cbTuongTac.setPreferredSize(new java.awt.Dimension(100, 50));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Tương tác");
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbQDLC, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbNangKhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTuongTac, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(159, 159, 159)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbUocMo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbXuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTanTam, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbCoiMo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(130, 130, 130))
            .addGroup(layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbJob, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lbM1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnToan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(56, 56, 56)
                            .addComponent(lbM2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnLy, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(lbM3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lbM7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnVan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(56, 56, 56)
                            .addComponent(lbM8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(lbM5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spnSu, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbM6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnDia, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbM4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbXuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbQDLC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTanTam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTuongTac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNangKhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbCoiMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbUocMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnToan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnLy, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbM7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnVan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbM8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbM5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnSu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbM6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnDia, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(lbJob, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        // TODO add your handling code here:
        Instance kk = GetTestInstance();
        String job = _Tree.trainingData.attribute(12).value(_Tree.TestData(kk));
        System.out.println(job);
        lbJob.setText("-----------" + job + "-----------");
    }//GEN-LAST:event_btnRunActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        _Parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRun;
    private javax.swing.JComboBox<String> cbCoiMo;
    private javax.swing.JComboBox<String> cbGioiTinh;
    private javax.swing.JComboBox<String> cbNangKhieu;
    private javax.swing.JComboBox<String> cbQDLC;
    private javax.swing.JComboBox<String> cbTanTam;
    private javax.swing.JComboBox<String> cbTuongTac;
    private javax.swing.JComboBox<String> cbUocMo;
    private javax.swing.JComboBox<String> cbXuHuong;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbJob;
    private javax.swing.JLabel lbM1;
    private javax.swing.JLabel lbM2;
    private javax.swing.JLabel lbM3;
    private javax.swing.JLabel lbM4;
    private javax.swing.JLabel lbM5;
    private javax.swing.JLabel lbM6;
    private javax.swing.JLabel lbM7;
    private javax.swing.JLabel lbM8;
    private javax.swing.ButtonGroup rbtnGroupKhoi;
    private javax.swing.JSpinner spnAnh;
    private javax.swing.JSpinner spnDia;
    private javax.swing.JSpinner spnHoa;
    private javax.swing.JSpinner spnLy;
    private javax.swing.JSpinner spnSinh;
    private javax.swing.JSpinner spnSu;
    private javax.swing.JSpinner spnToan;
    private javax.swing.JSpinner spnVan;
    // End of variables declaration//GEN-END:variables

}
