package org.touch.resource;

import org.touch.entity.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    @GET
    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }
    
    @GET
    @Path("/{id}")
    public Usuario buscarPorId(@PathParam("id") Long id) {
        return Usuario.findById(id);
    }
    
    @POST
    @Transactional
    public Response criar(Usuario usuario) {
        usuario.persist();
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }
    
    @PUT
    @Path("/{id}")
    public Usuario atualizar(@PathParam("id") Long id, Usuario usuario) {
        Usuario entidade = Usuario.findById(id);
        if (entidade == null) {
            throw new NotFoundException("Usuario não encontrado com o ID: " + id);
        }
        
        entidade.nome = usuario.nome;
        entidade.senha = usuario.senha;
        entidade.email = usuario.email;
        
        return entidade;
    }
    
    @DELETE
    @Transactional
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Usuario.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("Usuario não encontrado com o ID: " + id);
    }
}
