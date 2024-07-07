package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import DTO.BenhNhan;
import DTO.Tinh;
import connectSql.connect;

public class ThongTinBenhNhan extends JFrame {

    private JTextField jextfield_sdt;
    private JButton jbutton_timkiem;
    private JButton jbutton_xoa;
    private JButton jbutton_thoat;
    private JButton jbutton_sua;

    private JLabel jlabel_tenbn;
    private JLabel jlabel_sdt;
    private JLabel jlabel_diachi;
    private JLabel jlabel_ngaysinh;
    private JLabel jlabel_quequan;
    private JLabel jlabel_gioitinh;

    private JTextField jtextfield_tenbn;
    private JTextField jtextfield_sdt;
    private JTextField jtextfield_diachi;
    private JTextField jtextfield_ngaysinh;
    private JComboBox<String> CB_quequan;

    private JRadioButton jradio_nam;
    private JRadioButton jradio_nu;
    private ButtonGroup bg_gioitinh;

    private JButton jbutton_them;

    private JTable jtable_info;

    public ThongTinBenhNhan() {
        init();
        updateTinhComboBox();
        updateBenhNhanTable();
        setVisible(true);
    }

    public void init() {
        setTitle("Quản lý thông tin bệnh nhân");

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel jpanel_info = new JPanel();
        jpanel_info.setOpaque(false);
        jpanel_info.setLayout(new GridLayout(6, 2));

        Font font_1 = new Font("Arial", Font.BOLD, 20);

        jlabel_tenbn = new JLabel("Tên bệnh nhân");
        jlabel_tenbn.setFont(font_1);
        jlabel_sdt = new JLabel("Số điện thoại");
        jlabel_sdt.setFont(font_1);
        jlabel_diachi = new JLabel("Địa chỉ");
        jlabel_diachi.setFont(font_1);
        jlabel_ngaysinh = new JLabel("Ngày sinh");
        jlabel_ngaysinh.setFont(font_1);
        jlabel_quequan = new JLabel("Quê quán");
        jlabel_quequan.setFont(font_1);
        jlabel_gioitinh = new JLabel("Giới tính");
        jlabel_gioitinh.setFont(font_1);

        jtextfield_tenbn = new JTextField();
        jtextfield_tenbn.setFont(font_1);
        jtextfield_sdt = new JTextField();
        jtextfield_sdt.setFont(font_1);
        jtextfield_diachi = new JTextField();
        jtextfield_diachi.setFont(font_1);
        jtextfield_ngaysinh = new JTextField();
        jtextfield_ngaysinh.setFont(font_1);
        CB_quequan = new JComboBox<>();
        CB_quequan.setFont(font_1);

        jradio_nam = new JRadioButton("Nam");
        jradio_nam.setFont(font_1);
        jradio_nu = new JRadioButton("Nữ");
        jradio_nu.setFont(font_1);
        bg_gioitinh = new ButtonGroup();
        bg_gioitinh.add(jradio_nam);
        bg_gioitinh.add(jradio_nu);

        jpanel_info.add(jlabel_tenbn);
        jpanel_info.add(jtextfield_tenbn);
        jpanel_info.add(jlabel_sdt);
        jpanel_info.add(jtextfield_sdt);
        jpanel_info.add(jlabel_diachi);
        jpanel_info.add(jtextfield_diachi);
        jpanel_info.add(jlabel_ngaysinh);
        jpanel_info.add(jtextfield_ngaysinh);
        jpanel_info.add(jlabel_quequan);
        jpanel_info.add(CB_quequan);
        jpanel_info.add(jlabel_gioitinh);

        JPanel jpanel_gioitinh = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpanel_gioitinh.add(jradio_nam);
        jpanel_gioitinh.add(jradio_nu);
        jpanel_info.add(jpanel_gioitinh);

        JPanel jpanel_center = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        jextfield_sdt = new JTextField(20);
       
        jbutton_timkiem = new JButton("Tìm Kiếm theo số điện thoại");

        jbutton_timkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sdt = jextfield_sdt.getText().trim();
                if (!sdt.isEmpty()) {
                    timKiemBenhNhanTheoSDT(sdt);
                } else {
                    JOptionPane.showMessageDialog(ThongTinBenhNhan.this, "Vui lòng nhập số điện thoại để tìm kiếm!");
                }
            }
        });

        JButton jbutton_quaylai = new JButton("Làm mới");

        


        jbutton_quaylai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 DefaultTableModel modelTable = (DefaultTableModel) jtable_info.getModel();
                 modelTable.setRowCount(0);
                 updateBenhNhanTable();
                 
            }
        });

        leftPanel.add(jextfield_sdt);
        leftPanel.add(jbutton_timkiem);
        leftPanel.add(jbutton_quaylai);

        jtable_info = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tên bệnh nhân");
        model.addColumn("Sdt");
        model.addColumn("Địa chỉ");
        model.addColumn("Ngày sinh");
        model.addColumn("Quê quán");
        model.addColumn("Giới tính");

        jtable_info.setModel(model);
        jtable_info.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JScrollPane jscrollpane_table = new JScrollPane(jtable_info);
        jscrollpane_table.setBorder(new EmptyBorder(10, 10, 10, 10));
        jtable_info.addMouseListener(new MouseAdapter() {                           
            @Override                                                                 
            public void mouseClicked(MouseEvent e) {                                  
                int row = jtable_info.getSelectedRow();                             
                if (row != -1) {                                                      
                    String ddl= jtable_info.getValueAt(row, 0).toString();              
                    showSelectedRowData();                                          
                                                                                      
                }                                                                     
            }                                                                         
        });                                                                           

        jpanel_center.setLayout(new BorderLayout());
        jpanel_center.add(leftPanel, BorderLayout.NORTH);
        jpanel_center.add(jscrollpane_table, BorderLayout.CENTER);

        jbutton_them = new JButton("Thêm");

        jbutton_them.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertBenhNhanToTable();
            }
        });

        jbutton_xoa = new JButton("Xóa");

        jbutton_xoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 deleteBenhNhan();
            }
        });

        jbutton_sua = new JButton("Sửa");

        jbutton_sua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 updateBenhNhan();
            }
        });

        jbutton_thoat = new JButton("Thoát");

        jbutton_thoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // exitFrame();
            }
        });

        JPanel jpanel_button = new JPanel();
        jpanel_button.setLayout(new GridLayout(1, 4, 10, 0));
        jpanel_button.add(jbutton_them);
        jpanel_button.add(jbutton_xoa);
        jpanel_button.add(jbutton_sua);
        jpanel_button.add(jbutton_thoat);

        this.setLayout(new BorderLayout());
        this.add(jpanel_info, BorderLayout.NORTH);
        this.add(jpanel_center, BorderLayout.CENTER);
        this.add(jpanel_button, BorderLayout.SOUTH);
    }

    public void insertBenhNhanToTable() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();
            String sql = "INSERT INTO BenhNhan (TENBN, SDT, DIACHI, NGAYSINH, QUEQUAN, GIOITINH) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            String tenbn = jtextfield_tenbn.getText();
            String sdt = jtextfield_sdt.getText();
            String diachi = jtextfield_diachi.getText();
            String ngaysinh = jtextfield_ngaysinh.getText();
            String quequan = CB_quequan.getSelectedItem().toString();
            int mattp = getTinhByQueQuan(quequan);
            String gioitinh = jradio_nam.isSelected() ? "Nam" : "Nữ";

            ps.setString(1, tenbn);
            ps.setString(2, sdt);
            ps.setString(3, diachi);
            ps.setString(4, ngaysinh);
            ps.setInt(5, mattp);
            ps.setString(6, gioitinh);

            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm bệnh nhân thành công!");
                DefaultTableModel model = (DefaultTableModel) jtable_info.getModel();
                model.addRow(new Object[]{tenbn, sdt, diachi, ngaysinh, quequan, gioitinh});
             
            } else {
                JOptionPane.showMessageDialog(this, "Thêm bệnh nhân thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBenhNhan() {
        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();
            String sql = "UPDATE BenhNhan SET tenBN=?, sdt=?, ngaySinh=?, diaChi=?, gioiTinh=?, queQuan=?";
            
            ps = con.prepareStatement(sql);

            String tenbn = jtextfield_tenbn.getText();
            String sdt = jtextfield_sdt.getText();
            String diachi = jtextfield_diachi.getText();
            String ngaysinh = jtextfield_ngaysinh.getText();
            String quequan = CB_quequan.getSelectedItem().toString();
            int mattp = getTinhByQueQuan(quequan);
            String gioitinh = jradio_nam.isSelected() ? "Nam" : "Nữ";

            ps.setString(1, tenbn);
            ps.setString(2, sdt);
            ps.setString(3, ngaysinh);
            ps.setString(4, diachi);
            ps.setString(5, gioitinh);
            ps.setInt(6, mattp);
          

            result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Sửa bệnh nhân thành công!");
         
                DefaultTableModel model = (DefaultTableModel) jtable_info.getModel();
                int selectedRow = jtable_info.getSelectedRow();
                if (selectedRow != -1) {
                    model.setValueAt(tenbn, selectedRow, 0);
                    model.setValueAt(sdt, selectedRow, 1);
                    model.setValueAt(diachi, selectedRow, 2);
                    model.setValueAt(ngaysinh, selectedRow, 3);
                    model.setValueAt(quequan, selectedRow, 4);
                    model.setValueAt(gioitinh, selectedRow, 5);
                }
             
            } else {
                JOptionPane.showMessageDialog(this, "Sửa bệnh nhân thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getTinhByQueQuan(String quequan) {
    	   Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        int id = -1;
	        try {
		        con = getConnection();
		        String query = "SELECT MATTP FROM TINH WHERE TENTPP = ?";
		        ps = con.prepareStatement(query);
		        ps.setString(1, quequan);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		            id = rs.getInt("MATTP");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		    } 
		    return id;
		}
	            
    public void updateBenhNhanTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        DefaultTableModel model = (DefaultTableModel) jtable_info.getModel();


        try {
            con = getConnection();

	        String query = "SELECT tenBN,sdt,ngaySinh,diaChi,gioiTinh,queQuan FROM BenhNhan";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

           

            while (rs.next()) {
            	String tenbn = rs.getString("tenBN");
            	String sdt = rs.getString("sdt");
            	String ngaysinh = rs.getString("ngaySinh");
            	String diachi = rs.getString("diaChi");
            	String gioitinh = rs.getString("gioiTinh");
            	
            	int mattp = rs.getInt("queQuan");
            	String tenquequan = getTenQueQuanByMa(mattp);
            	
            	model.addRow(new Object[]{tenbn,sdt,diachi,ngaysinh,tenquequan,gioitinh});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }     
    
    public void showSelectedRowData() {
        DefaultTableModel model = (DefaultTableModel) jtable_info.getModel();
        int selectedRow = jtable_info.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một bệnh nhân để sửa!");
            return;
        }

        String tenbn = model.getValueAt(selectedRow, 0).toString();
        String sdt = model.getValueAt(selectedRow, 1).toString();
        String diachi = model.getValueAt(selectedRow, 2).toString();
        String ngaysinh = model.getValueAt(selectedRow, 3).toString();
        String quequan = model.getValueAt(selectedRow, 4).toString();
        String gioitinh = model.getValueAt(selectedRow, 5).toString();

        jtextfield_tenbn.setText(tenbn);
        jtextfield_sdt.setText(sdt);
        jtextfield_diachi.setText(diachi);
        jtextfield_ngaysinh.setText(ngaysinh);
        CB_quequan.setSelectedItem(quequan);
        if (gioitinh.equals("Nam")) {
            jradio_nam.setSelected(true);
        } else {
            jradio_nu.setSelected(true);
        }
    }

    public void deleteBenhNhan() {
    	 Connection con = null;
         PreparedStatement ps = null;
   

        DefaultTableModel model_table = (DefaultTableModel) jtable_info.getModel();
        int i_row = jtable_info.getSelectedRow();
        int luaChon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dòng đã chọn?");
        if (luaChon == JOptionPane.YES_OPTION) {
        	  try {
                  con = getConnection();

      	        String query = "DELETE FROM BenhNhan WHERE sdt=?";
                  ps = con.prepareStatement(query);
                  String sdt = model_table.getValueAt(i_row, 1).toString();
                  ps.setString(1, sdt);
        
                   int result = ps.executeUpdate();
                   if (result > 0) {
                       JOptionPane.showMessageDialog(this, "Xóa bệnh nhân thành công!");
                  	 DefaultTableModel modelTable = (DefaultTableModel) jtable_info.getModel();
                     modelTable.setRowCount(0);
                     updateBenhNhanTable();
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               }
        
        }
    }

    
    public String getTenQueQuanByMa(int mattp) {
 	   Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String tenque = null;
	        try {
		        con = getConnection();
		        String query = "SELECT TENTPP FROM TINH WHERE MATTP = ?";
		        ps = con.prepareStatement(query);
		        ps.setInt(1, mattp);
		        rs = ps.executeQuery();
		        
		        if (rs.next()) {
		        	tenque = rs.getString("TENTPP");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		    } 
		    return tenque;
		}

    
    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://ANDREW\\SQLEXPRESS:1433;databaseName=QuanLyBenhNhan;trustServerCertificate=true";
            String username = "sa";
            String password = "123456";
            c = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void updateTinhComboBox() {
        ArrayList<String> list_loaittp = fetchAllTinhTP();
        CB_quequan.removeAllItems();
        for (String loaidt : list_loaittp) {
            CB_quequan.addItem(loaidt);
        }
    }

    public ArrayList<String> fetchAllTinhTP() {
        ArrayList<String> listTinhTP = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT TENTPP FROM TINH";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listTinhTP.add(rs.getString("TENTPP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTinhTP;
    }

    public void timKiemBenhNhanTheoSDT(String sdt) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        DefaultTableModel model = (DefaultTableModel) jtable_info.getModel();
        model.setRowCount(0); // Xóa hết các dòng hiện tại trong bảng
        
        try {
            con = getConnection();
            String sql = "SELECT tenBN, sdt, ngaySinh, diaChi, gioiTinh, queQuan FROM BenhNhan WHERE sdt = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, sdt);
            rs = ps.executeQuery();

            boolean found = false; // Biến để kiểm tra có kết quả hay không

            while (rs.next()) {
                String tenbn = rs.getString("tenBN");
                String sdtResult = rs.getString("sdt");
                String ngaysinh = rs.getString("ngaySinh");
                String diachi = rs.getString("diaChi");
                String gioitinh = rs.getString("gioiTinh");

                int mattp = rs.getInt("queQuan");
                String tenquequan = getTenQueQuanByMa(mattp);

                model.addRow(new Object[]{tenbn, sdtResult, diachi, ngaysinh, tenquequan, gioitinh});
                found = true;
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bệnh nhân với số điện thoại này!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

   
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongTinBenhNhan().setVisible(true);
            }
        });
    }
}
