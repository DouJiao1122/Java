package Swing.dhk.file.my;

import Swing.dhk.file.af.common.json.AfJSON;
import Swing.dhk.file.af.swing.AfPanel;
import Swing.dhk.file.af.swing.layout.AfColumnLayout;
import Swing.dhk.file.af.swing.layout.AfRowLayout;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;



public class MyFrame extends JFrame
{
	JTextField textField = new JTextField(20); 	//文本框，用于保存已经存在的一个目录
	JTextField newField = new JTextField(20); 	//文本框，用于保存输入新的文件名称
	JComboBox<String>  sexField = new JComboBox<>(); 	//预设可选文本框盒子，用于保存预设格式
	JTextField customizeField = new JTextField(20);//文本框，用于保存输入新文件名的后缀格式

	JTextField showField = new JTextField(20);	//


	public MyFrame(String title)
	{
		super(title);//调用父类的构造方法

		// Content Pane
		JPanel root = new JPanel();//新建JPanel容器
		this.setContentPane(root); //向窗口添加容器
		root.setLayout(new BorderLayout());//设置容器布局

		// 初始化中间面板
		initMainPanel();

		// 初始化工具栏
		initToolBar();
	}

	// 初始化中间面板
	private void initMainPanel()
	{
		AfPanel mainPanel = new AfPanel();	//实例化类
		this.getContentPane().add(mainPanel, BorderLayout.CENTER); //向窗口添加容器
		mainPanel.padding(20); //容器内边距
		mainPanel.setLayout(new AfColumnLayout(15)); //容器内组件的间距
		
		// 第一行
		AfPanel row1 = new AfPanel(); //AfPanel实例化 row1 变量
		row1.setLayout(new AfRowLayout(10)); // 第一行组件的间距
		row1.add(new JLabel("输入路径"), "70px"); //添加文字
		row1.add(textField,"1w");	//添加一个 显示已经存在的一个目录的文本框

		
		AfPanel row2 = new AfPanel();	//AfPanel实例化 row2 变量
		row2.setLayout(new AfRowLayout(10));// 第二行组件的间距
		row2.add(new JLabel("新的名称"), "70px");//添加文字
		row2.add(newField, "1w");//添加一个文本框
		
		AfPanel row3 = new AfPanel(); //AfPanel实例化 row3 变量
		row3.setLayout(new AfRowLayout(10));// 第三行组件的间距
		row3.add(new JLabel("预设格式"), "70px");//添加文字
		row3.add(sexField, "1w");	//添加一个选项盒子

		sexField.addItem("png"); //预设png格式
		customizeField.setText("png");

		AfPanel row4 = new AfPanel();//AfPanel实例化 row4 变量
		row4.setLayout(new AfRowLayout(10));// 第四行组件的间距
		row4.add(new JLabel("自定义格式"), "70px");//添加文字
		row4.add(customizeField, "1w");//文本框，用于保存自定义格式




		mainPanel.add(row1, "30px"); //组件1大小
		mainPanel.add(row2, "30px"); //组件2大小
		mainPanel.add(row3, "30px"); //组件3大小
		mainPanel.add(row4, "30px"); //组件4大小

	}

	// 初始化工具栏
	public void initToolBar()
	{
		JToolBar toolBar = new JToolBar(); // 实例化类 toolBar 变量
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START); //把 toolBar 添加到窗口,并且布局为首行
		toolBar.setFloatable(true); //如果为 true，则工具栏可以移动；如果为 false，则工具栏不能移动

		// 添加按钮
		JButton newButton = createToolButton("new", "新建");
		JButton openButton = createToolButton("open", "打开");
		JButton saveButton = createToolButton("save", "保存");
		JButton startButton = createToolButton("start", "开始");
		toolBar.add(newButton);
		toolBar.add(openButton);
		toolBar.add(saveButton);
		toolBar.add(startButton);

		// 点击按钮 ( 这里是 Lambda表达式  的写法)
		newButton.addActionListener((e)->{
			//清空
			doNew();
		});
		openButton.addActionListener((e)->{
			//打开目录
			doOpen();
		});
		saveButton.addActionListener((e)->{
			//保存数据
			doSave();
		});
		startButton.addActionListener((e)->{
			//重命名
				Utils.reNameTo(textField.getText(),newField.getText(),customizeField.getText());

		});
	}

	//创建按钮
	private JButton createToolButton(String icon, String tooltip)
	{
		String iconPath = "icons/ic_" + icon + ".png"; //实例化字符串类型 变量iconPath 传入图片路径
		
		JButton button = new JButton(); //调用按钮实例
		button.setIcon(new ImageIcon(getClass().getResource(iconPath))); //给按钮设置icon图标
		button.setFocusPainted(false); //可以让里面那个focus的方框不显示
		button.setToolTipText(tooltip);//用来设置鼠标在Label上停留时显示提示信息的
		return button;//返回button变量
	}
	
	// 新建一份数据
	private void doNew()
	{
		//清空，恢复默认
		textField.setText("");
		newField.setText("");
		customizeField.setText("");
		sexField.setSelectedIndex(0);
	}
	
	//打开文件
	private void doOpen()
	{

		JFileChooser chooser = new JFileChooser();

		// 设置模式：仅选择目录
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// 显示对话框
		int ret = chooser.showOpenDialog(this);
		// 获取用户选择的结果
		if (ret == JFileChooser.APPROVE_OPTION)
		{
			// 结果为： 已经存在的一个目录
			File dir = chooser.getSelectedFile();
			textField.setText(dir.getAbsolutePath());
		}
	}
	
	// 保存当前数据
	private void doSave()
	{
		//trim() 方法用于删除字符串的头尾空白符
		String text = textField.getText().trim();
		String newFile = newField.getText().trim();
		String cus = customizeField.getText().trim();
		boolean sex = ( sexField.getSelectedIndex() == 1 );

		//判断字符串是否为空
		if(text.isEmpty() || newFile.isEmpty() || cus.isEmpty())
		{
			//提示输入的内容不能为空值
			JOptionPane.showMessageDialog(this, "输入不得为空!");
			return;
		}
		
		// 转成JSON对象
		JSONObject j1 = new JSONObject();
		j1.put("text", text);
		j1.put("newFile", newFile);
		j1.put("customize", cus);
		j1.put("sex", sex);
		
		// 打开文件对话框
		JFileChooser chooser = new JFileChooser();
		//FileNameExtensionFilter: 文件名后缀过滤器
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
		chooser.setFileFilter(filter);
		int ret = chooser.showSaveDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION)
		{
			// 结果为：用户要保存的文件的路径
			File file = chooser.getSelectedFile();
			if(! file.getName().endsWith(".stu"))
			{
				String filePath = file.getAbsolutePath();
				filePath += ".stu";
				file = new File(filePath);// 自动附加后缀名
			}
			
			// 注：Windows10下面，不能直接在根分区下创建文件
			try
			{
				AfJSON.toFile(j1, file, "UTF-8");
				JOptionPane.showMessageDialog(this, "已保存");
				System.out.println("保存至: " + file);
				doNew();// 清空显示
			} catch (Exception e)
			{				
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "出错:" + e.getMessage());
			}
		}
	}


}
