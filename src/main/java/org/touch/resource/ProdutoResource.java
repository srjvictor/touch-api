package org.touch.resource;

import org.touch.entity.Produto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/produtos")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    
    @GET
    public List<Produto> listarTodos() {
        return Produto.listAll();
    }
    
    @GET
    @Path("/{id}")
    public Produto buscarPorId(@PathParam("id") Long id) {
        return Produto.findById(id);
    }
    
    @POST
    @Transactional
    public Response criar(Produto produto) {
        produto.persist();
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }
    
    @PUT
    @Path("/{id}")
    public Produto atualizar(@PathParam("id") Long id, Produto produto) {
        Produto entidade = Produto.findById(id);
        if (entidade == null) {
            throw new NotFoundException("Produto não encontrado com o ID: " + id);
        }
        
        entidade.nome = produto.nome;
        entidade.descricao = produto.descricao;
        entidade.preco = produto.preco;
        
        return entidade;
    }
    
    @DELETE
    @Transactional
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        boolean excluido = Produto.deleteById(id);
        if (excluido) {
            return Response.noContent().build();
        }
        throw new NotFoundException("Produto não encontrado com o ID: " + id);
    }
}