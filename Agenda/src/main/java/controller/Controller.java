package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = {
	"/Controller",
	"/main",
	"/insert",
	"/select",
	"/update",
	"/delete",
	"/report"})
public class Controller extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dao. */
	DAO dao = new DAO();
	
	/** The contatos. */
	JavaBeans contatos = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller(){
		super();
	}


	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		String action = request.getServletPath();
		//		System.out.println(action);
		if(action.equals("/main")){
			contatos(request, response);
		}else if(action.equals("/insert")){
			AdicionarContatos(request, response);
		}else if(action.equals("/select")){
			listarContatos(request, response);
		}else if(action.equals("/update")){
			editarContatos(request, response);
		}else if(action.equals("/delete")){
			deletarContatos(request, response);
		}else if(action.equals("/report")){
			relatioDeContatos(request, response);
		}else{
			response.sendRedirect("index.html");
		}

	}

	// Listar contatos

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void contatos(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		ArrayList<JavaBeans> lista = dao.listarContatos();

		// encaminhar a lista ao documento
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}


	// Novo contato

	/**
	 * Adicionar contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void AdicionarContatos(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		contatos.setNome(request.getParameter("nome"));
		contatos.setFone(request.getParameter("fone"));
		contatos.setEmail(request.getParameter("email"));
		// inserir contatos
		dao.inserirContatos(contatos);
		// redirecionar 
		response.sendRedirect("main");

	}


	/**
	 * Listar contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Editar contatos
	protected void listarContatos(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		// receber id do contatos
		contatos.setIdcon(request.getParameter("idcon"));

		// executar método selecionr contatos
		dao.selecionarContatos(contatos);

		// setar os atribustos do formularios
		request.setAttribute("idcon", contatos.getIdcon());
		request.setAttribute("nome", contatos.getNome());
		request.setAttribute("fone", contatos.getFone());
		request.setAttribute("email", contatos.getEmail());

		// Encamnhar ao documento ecitar 
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);


	}


	/**
	 * Editar contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContatos(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		// setar as variáveis JavaBeans

		contatos.setIdcon(request.getParameter("idcon"));
		contatos.setNome(request.getParameter("nome"));
		contatos.setFone(request.getParameter("fone"));
		contatos.setEmail(request.getParameter("email"));

		// executar alteração do contato
		dao.alterarContatos(contatos);

		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");

	}


	/**
	 * Deletar contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void deletarContatos(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		// setart a variável idcon
		contatos.setIdcon(request.getParameter("idcon"));

		//deletar na tabela
		dao.deletarContatos(contatos);

		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}


	/**
	 * Relatio de contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void relatioDeContatos(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Document documento = new Document();
		try{
			//Tipo de documento
			response.setContentType("apllication/pdf");
			//Nome do documento
			response.addHeader("Content-Disposition", "inline;filename= " + "contatos.pdf");
			//Criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrir o documento>- conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento.add(new Paragraph(" "));
			//criar tablela
			PdfPTable tabela = new PdfPTable(3);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// popular as tabelas com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();

			for (int i = 0; i < lista.size(); i++){
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}

			documento.add(tabela);
			documento.close();
		}catch(Exception e){
			e.printStackTrace();
			documento.close();
		}
	}

}

