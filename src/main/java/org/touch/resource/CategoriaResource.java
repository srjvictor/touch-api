package org.touch.resource;

import org.touch.entity.Categoria;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categorias")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {
    
    @GET
    public List<Categoria> listarTodos() {
        return Categoria.listAll();
    }
    
    @GET
    @Path("/{id}")
    public Categoria buscarPorId(@PathParam("id") Long id) {
        return Categoria.findById(id);
    }
    
    @POST
    @Transactional
    public Response criar(Categoria Categoria) {
        Categoria.persist();
        return Response.status(Response.Status.CREATED).entity(Categoria).build();
    }
    
    @PUT
    @Path("/{id}")
    public Categoria atualizar(@PathParam("id") Long id, Categoria categoria) {
        Categoria entidade = Categoria.findById(id);
        if (entidade == null) {
            throw new NotFoundException("Categoria não encontrado com o ID: " + id);
        }
        
        entidade.nome = categoria.nome;

        return entidade;
    }
    
    @DELETE
    @Transactional
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Categoria.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("Categoria não encontrado com o ID: " + id);
    }
}