package view;

import access.builder.AvatarBuilder;
import access.DTO.AvatarDTO;
import controller.ModelController;
import entities.Avatar;
import java.io.IOException;
import javax.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chrschae
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    @Inject
    private ModelController ctrl;
    private AvatarDTO imageDAO;

    /**
     *
     * @throws ServletException
     */
    public void init() throws ServletException {
        this.imageDAO = AvatarDTO.toAvatarDTO(AvatarBuilder.create().build());
    }

    /**
     * On HttpRequest, this method returns an avatar image from the Model to be
     * displayed in the view.The Content Type of the response is "image/jpeg".
     *
     * @param request with parameter ID for the imageHash
     * @param response HttpResponse containing the requested Image
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String imageId = request.getParameter("id");
        int imageHash = Integer.parseInt(imageId);

        if (imageId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        Avatar image = ctrl.getAvatar(imageHash).toAvatar();

        if (image == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        response.reset();
        response.setContentType(image.getContentType());
        response.setContentLength(image.getImage().length);

        response.getOutputStream().write(image.getImage());
    }

}
