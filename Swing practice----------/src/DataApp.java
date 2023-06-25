import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DataApp {
	
	Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver registered");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp11", "root", "abc123");
		return con;
	}
	
	
	void frames() {
		JFrame f = new JFrame("Sneha's Application");
		
		
		//add panel
		 JPanel panel1 = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Image image = new ImageIcon("E:\\Asterisc Tuition Classes\\2195cc7cbeb0e9b9fd10005fe6d6abcc.jpg").getImage();
	                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	            }
	        };

	        // Set the layout manager for the panel
	        panel1.setLayout(null);
	        f.getContentPane().add(panel1);
		
		// Add data
		JLabel ad = new JLabel("Add data");
		ad.setBounds(160, 60, 100, 40);
		JLabel ja = new JLabel("Roll:");
		ja.setBounds(100, 100, 100, 40);
		JLabel n = new JLabel("Name:");
		n.setBounds(100, 140, 100, 40);
		JTextField tx1 = new JTextField();
		tx1.setBounds(150, 100, 100, 40);
		JTextField tx2 = new JTextField();
		tx2.setBounds(150, 150, 100, 40);
		JButton a = new JButton("Add Data");
		a.setBounds(150, 200, 100, 40);
		
		//add data
				a.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int roll = Integer.parseInt(tx1.getText());
						String name = tx2.getText();
						
						try {
							Connection con = connect();
							Statement st = con.createStatement();
							int ab = st.executeUpdate("INSERT INTO emp11.crud111 VALUES(" + roll + ",'" + name + "')");

							if (ab > 0) {
								System.out.println("Data inserted");
								JOptionPane.showMessageDialog(a, "DATA INSERTED");
							} else {
								System.out.println("Data not inserted");
								JOptionPane.showMessageDialog(a, "DATA NOT INSERTED");
							}
						} catch (ClassNotFoundException | SQLException ex) {
							ex.printStackTrace();
						}
					}
				});
				
		
		// Show data
		JLabel sh = new JLabel("Show data");
		sh.setBounds(400, 60, 100, 40);
		
		JButton sho = new JButton("Show data");
		sho.setBounds(380, 150, 100, 40);
		JLabel lb1 = new JLabel();
		lb1.setFont(new Font("curlz MT", Font.BOLD, 20));
		lb1.setBounds(380, 150, 100, 100);
		lb1.setForeground(Color.magenta);
		
		JFrame frame = new JFrame("Employee Data");
        JTable table = new JTable();

        // Create the table model and set it to the JTable
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        // Add columns to the table model
        model.addColumn("ID");
        model.addColumn("Name");

        // Create a JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JPanel to hold the scroll pane
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.add(panel);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);

        // ActionListener code
        sho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try {
                    con = connect();
                } catch (ClassNotFoundException | SQLException e2) {
                    e2.printStackTrace();
                }
                PreparedStatement st = null;
                try {
                    st = con.prepareStatement("SELECT * FROM emp11.crud111");
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

                ResultSet rs = null;
                try {
                    rs = st.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        // Add data to the table model
                        model.addRow(new Object[]{id, name});
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
		
		
		//search
		
		JLabel search = new JLabel("Search data");
		search.setBounds(600, 60, 100, 40);
		
		JButton sr = new JButton("Search data");
		sr.setBounds(580, 150, 120, 40);
		JTextField tx6 = new JTextField();
		tx6.setBounds(590, 100, 100, 40);
		JLabel lb4 = new JLabel();
		lb4.setFont(new Font("curlz MT", Font.BOLD, 20));
		lb4.setBounds(580, 200, 100, 80);
		lb4.setForeground(Color.magenta);
		//search 
				sr.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						boolean found = false;
						int roll = Integer.parseInt(tx6.getText());
						Connection con = null;
						try {
							con = connect();
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						PreparedStatement st = null;
						try {
							st = con.prepareStatement("SELECT * FROM emp11.crud111 where roll=?");
							st.setInt(1, roll);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						ResultSet rs = null;
						try {
							rs = st.executeQuery();
							while (rs.next()) {

								System.out.println(rs.getInt(1) + " " + rs.getString(2) );
								//lb2.setText(rs.getInt(1) + " " + rs.getString(2)  );
								JOptionPane.showMessageDialog(search, "DATA FOUND");

								found = true;

							}
							if (!found) {
								JOptionPane.showMessageDialog(search, "DATA NOT FOUND");
							}
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						

					} });
				
				
	//update
		JLabel u = new JLabel("Update data");
		u.setBounds(1090, 60, 100, 40);
		
		JLabel j = new JLabel("Roll:");
		j.setBounds(1040, 100, 100, 40);
		JTextField tx4 = new JTextField();
		tx4.setBounds(1080, 100, 100, 40);
		JLabel n1 = new JLabel("Name:");
		n1.setBounds(1040, 150, 100, 40);
		
		JTextField tx5 = new JTextField();
		tx5.setBounds(1080, 150, 100, 40);
		JButton up = new JButton("Update");
		up.setBounds(1080, 200, 100, 40);
		JLabel lb2 = new JLabel();
		lb2.setFont(new Font("curlz MT", Font.BOLD, 20));
		lb2.setBounds(1000, 200, 100, 40);
		lb2.setForeground(Color.magenta);
		
		//update
		up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int roll = Integer.parseInt(tx4.getText());
				String name = tx5.getText();
				try {
					Connection con = connect();
					PreparedStatement st = con.prepareStatement("update emp11.crud111 set name=?  where roll=?");
					st.setString(1, name);
					st.setInt(2, roll);
					int ab = st.executeUpdate();
					

					if (ab > 0) {
						System.out.println("Data UPDATED");
						JOptionPane.showMessageDialog(up, "DATA UPDATED");
					} else {
						System.out.println("Data not UPDATED");
						JOptionPane.showMessageDialog(up, "DATA NOT UPDATED");
					}
				} catch (ClassNotFoundException | SQLException ex) {
					ex.printStackTrace();
				}
				
				
			}
		});
		
		//delete
				JLabel delete = new JLabel("Delete data");
				delete.setBounds(850, 60, 100, 40);
				
				JButton de = new JButton("Delete data");
				de.setBounds(830, 150, 120, 40);
				JTextField tx7 = new JTextField();
				tx7.setBounds(840, 100, 100, 40);
				
				
				de.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int roll = Integer.parseInt(tx7.getText());
						
						try {
							Connection con = connect();
							PreparedStatement st = con.prepareStatement("delete from emp11.crud111  where roll=?");
							
							st.setInt(1, roll);
							int abc = st.executeUpdate();
							

							if (abc > 0) {
								System.out.println("Data deleted");
								JOptionPane.showMessageDialog(de, "DATA deleted");
							} else {
								System.out.println("Data not deleted");
								JOptionPane.showMessageDialog(de, "DATA NOT deleted");
							}
						} catch (ClassNotFoundException | SQLException ex) {
							ex.printStackTrace();
						}
						
						
					}
				});
				
				
		
		/////show data
		/*sho.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = connect();
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				PreparedStatement st = null;
				try {
					st = con.prepareStatement("SELECT * FROM emp11.crud111");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				ResultSet rs = null;
				try {
					rs = st.executeQuery();
					 StringBuilder data = new StringBuilder();
					while (rs.next()) {

						    int id = rs.getInt(1);
			                String name = rs.getString(2);
			                data.append(id).append(" ").append(name).append(" \n"); // Append the data with a line break
			                //System.out.println(id +" "+name );
			               
						}
					 lb1.setText(data.toString());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			} });*/
		
		
		
       /* JFrame frame = new JFrame("Employee Data");
        JTable table = new JTable();

        // Create the table model and set it to the JTable
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        // Add columns to the table model
        model.addColumn("ID");
        model.addColumn("Name");

        // Create a JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JPanel to hold the scroll pane
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.add(panel);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);

        // ActionListener code
        sho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;
                try {
                    con = connect();
                } catch (ClassNotFoundException | SQLException e2) {
                    e2.printStackTrace();
                }
                PreparedStatement st = null;
                try {
                    st = con.prepareStatement("SELECT * FROM emp11.crud111");
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

                ResultSet rs = null;
                try {
                    rs = st.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        // Add data to the table model
                        model.addRow(new Object[]{id, name});
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });*/
		
        
      //  add image
       
		// Add components to the frame
        f.add(panel1);
        
        panel1.add(search);
panel1.add(ad);
        panel1.add(sr);

        panel1.add(lb4);
        panel1.add(tx6);
        panel1.add(ja);
		panel1.add(n);
		panel1.add(tx1);
		panel1.add(tx2);
		panel1.add(a);
		panel1.add(sh);
		
		panel1.add(sho);
		panel1.add(lb1);
		panel1.add(u);
		panel1.add(tx5);
		panel1.add(tx4);
		panel1.add(up);
		panel1.add(lb2);
		panel1.add(j);
		panel1.add(n1);
        panel1.add(delete);
		
		panel1.add(de);
		panel1.add(tx7);
		
		
		f.setSize(1400, 800);
		f.setLayout(null);
		f.setVisible(true);
		panel1.setSize(1400, 800);
		panel1.setLayout(null);
		panel1.setVisible(true);
	}
	
	public static void main(String[] args) {
		DataApp dt = new DataApp();
		dt.frames();
	}
}
