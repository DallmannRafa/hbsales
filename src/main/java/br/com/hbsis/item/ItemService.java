package br.com.hbsis.item;

import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.pedido.PedidoService;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final IItemRepository iItemRepository;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public ItemService(IItemRepository iItemRepository, PedidoService pedidoService, ProdutoService produtoService) {
        this.iItemRepository = iItemRepository;
        this.pedidoService = pedidoService;

        this.produtoService = produtoService;
    }

    public ItemDTO save(ItemDTO itemDTO) {

        this.vaildate(itemDTO);

        Item item = new Item();

        Optional<Pedido> pedidoOptional = pedidoService.findByIdOptional(itemDTO.getPedido());
        if (pedidoOptional.isPresent()) {
            item.setPedido(pedidoOptional.get());
        }

        Optional<Produto> produtoOptional = produtoService.findByIdOptional(itemDTO.getProduto().getId());
        if (produtoOptional.isPresent()) {
            item.setProduto(produtoOptional.get());
        }

        item.setQuantidade(itemDTO.getQuantidade());

        item = iItemRepository.save(item);

        return ItemDTO.of(item);
    }

    public ItemDTO update(ItemDTO itemDTO, Long id) {
        Optional<Item> itemOptional = iItemRepository.findById(id);

        if (itemOptional.isPresent()) {

            this.vaildate(itemDTO);

            Item item = new Item();

            Optional<Pedido> pedidoOptional = pedidoService.findByIdOptional(itemDTO.getPedido());
            if (pedidoOptional.isPresent()) {
                item.setPedido(pedidoOptional.get());
            }

            Optional<Produto> produtoOptional = produtoService.findByIdOptional(itemDTO.getProduto().getId());
            if (produtoOptional.isPresent()) {
                item.setProduto(produtoOptional.get());
            }

            item.setQuantidade(itemDTO.getQuantidade());

            item = iItemRepository.save(item);

            return ItemDTO.of(item);
        }

        throw new IllegalArgumentException("ID não existe");
    }

    public ItemDTO findById(Long id) {

        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            return ItemDTO.of(itemOptional.get());
        }

        throw new IllegalArgumentException("ID não existe");

    }

    public Item findItemById(Long id) {
        Optional<Item> itemOptional = this.iItemRepository.findById(id);

        if (itemOptional.isPresent()) {
            return itemOptional.get();
        }

        throw new IllegalArgumentException("Id não existe");
    }

    public void delete (Long id) {
        this.iItemRepository.deleteById(id);
    }

    public void vaildate(ItemDTO itemDTO) {

        if (itemDTO == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }

        if (StringUtils.isEmpty(itemDTO.getPedido().toString())) {
            throw new IllegalArgumentException("Pedido não pode ser nulo");
        }

        if (StringUtils.isEmpty(itemDTO.getProduto().toString())) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (StringUtils.isEmpty(String.valueOf(itemDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Quantidade não pode ser nula");
        }
    }

    public List<Item> findByPedido(Pedido pedido) {
        List<Item> itens = this.iItemRepository.findByPedido(pedido);

        if (!itens.isEmpty()) {
            return itens;
        }

        throw new IllegalArgumentException("Não existem itens por esse pedido");
    }
}
