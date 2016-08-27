package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Contact;
import exception.AgeInputException;
import exception.EmailInputException;
import exception.GenderNullException;
import exception.NameInputException;
import exception.NameNullException;
import exception.NameRepeatException;
import exception.QqInputException;
import service.ContactService;
import service.serviceImpl.ContactServiceImpl;

/**
 * Servlet implementation class AddContactServlet
 */
@WebServlet("/AddContactServlet")
public class AddContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		//1.���ղ���
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		String email = request.getParameter("email");
		String qq = request.getParameter("qq");
		
		//��װ��Contact����
		Contact contact = new Contact();
		contact.setName(name);
		contact.setGender(gender);
		contact.setAge(age);
		contact.setEmail(email);
		contact.setQq(qq);
		
		ContactService service = new ContactServiceImpl();
		//2.����dao���������ϵ�˵ķ���
		try {
			service.addContact(contact);
			//3.��ת����ѯ��ϵ�˵�ҳ��
			response.sendRedirect(request.getContextPath()+"/ListContactServlet");
		} catch (QqInputException e) {
			request.setAttribute("msg6", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);
		} catch (EmailInputException e) {
			request.setAttribute("msg5", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);
		} catch (AgeInputException e) {
			request.setAttribute("msg4", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);
		} catch (GenderNullException e) {
			request.setAttribute("msg7", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);
		} catch (NameInputException e) {
			request.setAttribute("msg3", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);
		} catch (NameRepeatException e) {
			request.setAttribute("msg1", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);
		} catch (NameNullException e) {
			request.setAttribute("msg2", e.getMessage());
			request.setAttribute("contact", contact);
			request.getRequestDispatcher("/addContact.jsp").forward(request, response);

		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}