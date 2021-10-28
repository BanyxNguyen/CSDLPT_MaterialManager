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
import javax.swing.JSpinner;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

/**
 *
 * @author BenNguyen
 */
public class Main extends javax.swing.JFrame {

    public static Map<String, ContainerMap> Attributes = new Hashtable<>();
    public static Map<String, String> Jobs = new Hashtable<>();
    private final JSpinner[][] ListSpinnerSubject;
    private final JSpinner[] ListSpinner;
    private final JComboBox[] ListComboBox;

    private JobDecisionTree _Tree;
    private JFrame _Parent;

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

        InitMapCombobox();
        InitJobs();

        this.ListSpinnerSubject = new JSpinner[][]{
            {spnToan, spnLy, spnHoa},
            {spnToan, spnHoa, spnSinh},
            {spnVan, spnSu, spnDia},
            {spnToan, spnAnh, spnVan}
        };

        this.ListSpinner = new JSpinner[]{
            spnToan, spnLy, spnHoa,
            spnAnh, spnVan, spnSinh,
            spnSu, spnDia, spnTanTam,
            spnTuongTac, spnCoiMo
        };

        this.ListComboBox = new JComboBox[]{
            cbGioiTinh, cbXuHuong,
            cbxQuyetDinh, cbNangKhieu,
            cbUocMo
        };

        _Parent.setVisible(false);
        System.out.println(_Tree.performTraining());
    }

    private void InitMapCombobox() {
        Main.Attributes.put("GT", new ContainerMap(cbGioiTinh, new String[]{"nam", "nu"}));
        Main.Attributes.put("XuHuong", new ContainerMap(cbXuHuong, new String[]{"noi", "ngoai"}));
        Main.Attributes.put("QD", new ContainerMap(cbxQuyetDinh, new String[]{"lytri", "tinhcam"}));
//        Main.Attributes.put("TanTam", new ContainerMap(cbTanTam, new String[]{"tb", "kha", "cao"}));
//        Main.Attributes.put("TuongTac", new ContainerMap(cbTuongTac, new String[]{"tb", "kha", "cao"}));
//        Main.Attributes.put("CoiMo", new ContainerMap(cbCoiMo, new String[]{"chuatot", "kha", "tot"}));
        Main.Attributes.put("NangKhieu", new ContainerMap(cbNangKhieu, new String[]{
            "cahat",
            "hhkg",
            "hoatngon",
            "kinhdoanh",
            "may",
            "ttg",
            "tuduynhanh",
            "ve"
        }));
        Main.Attributes.put("UocMo", new ContainerMap(cbUocMo, new String[]{
            "htcd",
            "khoinghiep",
            "ksat",
            "nccn",
            "nckh",
            "nctp",
            "nctt",
            "nghethuat",
            "ondinh",
            "sangtao",
            "tuduy",
            "tuongtac"
        }));
    }

    private void InitJobs() {
        Main.Jobs.put("CNTT", "Công Nghệ Thông Tin");
        Main.Jobs.put("CNTP", "Công Nghệ Thực Phẩm");
        Main.Jobs.put("DTU", "Điện Tử");
        Main.Jobs.put("KTS", "Kiến Trúc Sư");
        Main.Jobs.put("MRT", "Marketing");
        Main.Jobs.put("QTKD", "Quản Trị Kinh Doanh");
        Main.Jobs.put("TCNH", "Tài Chính Ngân Hàng");
        Main.Jobs.put("TKTT", "Thiết Kế Thời Trang");
        Main.Jobs.put("TNHAC", "Thanh Nhạc");
    }

    private float _parseFloat(Object num) {
        if (num != null) {
            return Float.parseFloat(num.toString());
        } else {
            return 0F;
        }
    }

    private float[] GetPointABCD() {
        float[] temps = new float[4];
        Integer index = 0;
        for (JSpinner[] items : ListSpinnerSubject) {
            for (JSpinner item : items) {
                temps[index] += _parseFloat(item.getValue()) / 3;
            }
            index++;
        }
        return temps;
    }

    private float GetValueUnmap(float[] ps, String name) {
        float res = 0F;
        switch (name) {
            case "KhoiA": {
                res = ps[0];
                break;
            }
            case "KhoiB": {
                res = ps[1];
                break;
            }
            case "KhoiC": {
                res = ps[2];
                break;
            }
            case "KhoiD": {
                res = ps[3];
                break;
            }
            case "TanTam": {
                String t = spnTanTam.getValue().toString();
                res = Float.parseFloat(t);
                break;
            }
            case "TuongTac": {
                String t = spnTuongTac.getValue().toString();
                res = Float.parseFloat(t);
                break;
            }
            case "CoiMo": {
                String t = spnCoiMo.getValue().toString();
                res = Float.parseFloat(t);
                break;
            }
        }
        return res;
    }

    private boolean CheckUnmap(String name) {
        return "KhoiA".equals(name)
                || "KhoiB".equals(name)
                || "KhoiC".equals(name)
                || "KhoiD".equals(name)
                || "TanTam".equals(name)
                || "TuongTac".equals(name)
                || "CoiMo".equals(name);
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

            if (CheckUnmap(name)) {
                float temp = GetValueUnmap(points, name);
                ii.setValue(index++, temp);
                System.out.println(name + " " + temp);
            } else {
                ContainerMap conMap = Main.Attributes.get(name);
                Integer idx = conMap.Comb.getSelectedIndex();
                if (idx <= 0) {
                    ii.setMissing(index++);
                    System.out.println(name + " " + ii.value(index - 1));
                } else {
                    ii.setValue(index++, conMap.Value[idx - 1]);
                    System.out.println(name + " " + conMap.Value[idx - 1]);
                }
            }
        }
        return ii;
    }

    private void ClearAllBox() {
        spnToan.setValue(0F);
        spnLy.setValue(0F);
        spnHoa.setValue(0F);
        spnAnh.setValue(0F);
        spnVan.setValue(0F);
        spnSinh.setValue(0F);
        spnSu.setValue(0F);
        spnDia.setValue(0F);

        spnTanTam.setValue(0);
        spnTuongTac.setValue(0);
        spnCoiMo.setValue(0);

        cbGioiTinh.setSelectedIndex(0);
        cbXuHuong.setSelectedIndex(0);
        cbxQuyetDinh.setSelectedIndex(0);
        cbNangKhieu.setSelectedIndex(0);
        cbUocMo.setSelectedIndex(0);

        lbJob.setText("Tư vấn ngành nghề");
    }

    private boolean CheckAllValueIsChange() {
        for (JSpinner item : ListSpinner) {
            float t = _parseFloat(item.getValue());
            if (t > 0) {
                return true;
            }
        }

        for (JComboBox item : ListComboBox) {
            Integer t = item.getSelectedIndex();
            if (t > 0) {
                return true;
            }
        }

        return false;
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
        lbGioiTinh = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox<>();
        lbM1 = new javax.swing.JLabel();
        cbXuHuong = new javax.swing.JComboBox<>();
        lbXuHuong = new javax.swing.JLabel();
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
        lbNangKhieu = new javax.swing.JLabel();
        cbNangKhieu = new javax.swing.JComboBox<>();
        lbUocMo = new javax.swing.JLabel();
        cbUocMo = new javax.swing.JComboBox<>();
        lbQuyetDinh = new javax.swing.JLabel();
        cbxQuyetDinh = new javax.swing.JComboBox<>();
        spnTanTam = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        spnTuongTac = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        spnCoiMo = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        lbJob = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ứng Dựng Hỗ Trợ Tư Vấn Chọn Nghề");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(891, 660));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lbGioiTinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbGioiTinh.setText("Giới tính");
        lbGioiTinh.setToolTipText("Giới tính sẽ ảnh hưởng đến ngành phù hợp với từng giới");
        lbGioiTinh.setPreferredSize(new java.awt.Dimension(100, 50));

        cbGioiTinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "Nam", "Nữ" }));
        cbGioiTinh.setPreferredSize(new java.awt.Dimension(100, 50));

        lbM1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbM1.setText("Toán");
        lbM1.setPreferredSize(new java.awt.Dimension(100, 50));

        cbXuHuong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbXuHuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "Hướng nội", "Hướng ngoại" }));
        cbXuHuong.setPreferredSize(new java.awt.Dimension(100, 50));

        lbXuHuong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbXuHuong.setText("Xu hướng");
        lbXuHuong.setToolTipText("Là 1 người có tính cách hướng nội hay hướng ngoại. Những ngành nào sẽ phù hợp với người hướng ngoại, cũng như người hướng nội.");
        lbXuHuong.setPreferredSize(new java.awt.Dimension(100, 50));

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
        jLabel4.setToolTipText("Tính tận tâm được đánh giá trên thang điểm 1 đến 10");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 50));

        lbNangKhieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbNangKhieu.setText("Năng khiếu");
        lbNangKhieu.setToolTipText("Những năng khiếu nào sẽ phù hợp, bổ trợ tốt cho các ngành học nào.");
        lbNangKhieu.setPreferredSize(new java.awt.Dimension(100, 50));

        cbNangKhieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbNangKhieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "Ca hát", "Hoạ hình không gian", "Hoạt ngôn", "Kinh doanh", "May vá", "Tính toán giỏi", "Tư duy Nhanh", "Vẽ" }));
        cbNangKhieu.setPreferredSize(new java.awt.Dimension(100, 50));

        lbUocMo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbUocMo.setText("Ước mơ");
        lbUocMo.setToolTipText("Sợ thích, mong muốn về công việc mình sẽ học và làm.");
        lbUocMo.setPreferredSize(new java.awt.Dimension(100, 50));

        cbUocMo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbUocMo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "Hỗ trợ cộng đồng", "Khởi nghiệp", "Khảo sát", "Nghiên cứu công nghệ", "Nghiên cứu khoa học", "Nghiên cứu thực phẩm", "Nghiên cứu thị trường", "Nghệ thuật", "Ổn dịnh", "Sáng Tạo", "Tư duy", "Tương tác" }));
        cbUocMo.setPreferredSize(new java.awt.Dimension(100, 50));

        lbQuyetDinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbQuyetDinh.setText("Quyết định");
        lbQuyetDinh.setToolTipText(" Nếu phải đưa ra 1 lựa chọn quan trọng. Bạn thường đưa ra quyết định dựa theo cảm xúc hay sẽ suy nghĩ thấu đáo.");
        lbQuyetDinh.setPreferredSize(new java.awt.Dimension(100, 50));

        cbxQuyetDinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbxQuyetDinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<None>", "Lý trí", "Tình cảm" }));
        cbxQuyetDinh.setPreferredSize(new java.awt.Dimension(100, 50));

        spnTanTam.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Tương tác");
        jLabel11.setToolTipText("Khả năng tương tác được đánh giá trên thang điểm 1 đến 10");
        jLabel11.setPreferredSize(new java.awt.Dimension(100, 50));

        spnTuongTac.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Cởi mở");
        jLabel12.setToolTipText("Tính cởi mở được đánh giá trên thang điểm 1 đến 10");
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 50));

        spnCoiMo.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        lbJob.setBackground(new java.awt.Color(51, 51, 51));
        lbJob.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbJob.setForeground(new java.awt.Color(255, 255, 255));
        lbJob.setText("Tư vấn ngành nghề");
        lbJob.setAutoscrolls(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbJob, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbJob, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setText("Cài lại");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbUocMo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbUocMo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbQuyetDinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxQuyetDinh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbXuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbXuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbNangKhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbNangKhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbM7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnVan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbM1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnToan, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(64, 64, 64)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbM8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(spnSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbM2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(spnLy, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(88, 88, 88)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbM5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(spnSu, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbM3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(spnHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(83, 83, 83)
                                                .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnTanTam, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(123, 123, 123)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnTuongTac, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbM6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnDia, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbM4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spnAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnCoiMo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbXuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbXuHuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNangKhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNangKhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbQuyetDinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxQuyetDinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUocMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbUocMo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnTanTam, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnTuongTac, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lbM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnToan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lbM7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnVan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lbM2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnLy, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lbM8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbM3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbM5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnSu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnCoiMo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbM4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbM6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnDia, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        try {
            boolean check = CheckAllValueIsChange();
            if (check) {
                Instance kk = GetTestInstance();
                String job = _Tree.trainingData.attribute(12).value(_Tree.TestData(kk));
                System.out.println(job);
                String temp, resultJob;
                temp = Main.Jobs.get(job);
                if (temp != null) {
                    resultJob = temp;
                } else {
                    resultJob = job;
                }
                lbJob.setText("Nghề phù hợp: " + resultJob);
            } else {
                lbJob.setText("Vui lòng nhập thông tin");
//                123
            }
        } catch (Exception e) {
            lbJob.setText("Không xác định");
            System.out.println(e.toString());
        }


    }//GEN-LAST:event_btnRunActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        _Parent.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        ClearAllBox();
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRun;
    private javax.swing.JComboBox<String> cbGioiTinh;
    private javax.swing.JComboBox<String> cbNangKhieu;
    private javax.swing.JComboBox<String> cbUocMo;
    private javax.swing.JComboBox<String> cbXuHuong;
    private javax.swing.JComboBox<String> cbxQuyetDinh;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbGioiTinh;
    private javax.swing.JLabel lbJob;
    private javax.swing.JLabel lbM1;
    private javax.swing.JLabel lbM2;
    private javax.swing.JLabel lbM3;
    private javax.swing.JLabel lbM4;
    private javax.swing.JLabel lbM5;
    private javax.swing.JLabel lbM6;
    private javax.swing.JLabel lbM7;
    private javax.swing.JLabel lbM8;
    private javax.swing.JLabel lbNangKhieu;
    private javax.swing.JLabel lbQuyetDinh;
    private javax.swing.JLabel lbUocMo;
    private javax.swing.JLabel lbXuHuong;
    private javax.swing.ButtonGroup rbtnGroupKhoi;
    private javax.swing.JSpinner spnAnh;
    private javax.swing.JSpinner spnCoiMo;
    private javax.swing.JSpinner spnDia;
    private javax.swing.JSpinner spnHoa;
    private javax.swing.JSpinner spnLy;
    private javax.swing.JSpinner spnSinh;
    private javax.swing.JSpinner spnSu;
    private javax.swing.JSpinner spnTanTam;
    private javax.swing.JSpinner spnToan;
    private javax.swing.JSpinner spnTuongTac;
    private javax.swing.JSpinner spnVan;
    // End of variables declaration//GEN-END:variables

}
