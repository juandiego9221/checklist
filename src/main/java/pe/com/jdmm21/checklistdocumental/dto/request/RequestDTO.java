package pe.com.jdmm21.checklistdocumental.dto.request;

import java.util.List;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;

public class RequestDTO {
    private BranchDTO branch;
    private ChannelDTO channel;
    private CustomerDTO customer;
    private DocumentProcessDTO documentProcess;
    private ProductDTO product;
    private List<MetadataDTO> metadata;

    /**
     * @return BranchDTO return the branch
     */
    public BranchDTO getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    /**
     * @return ChannelDTO return the channel
     */
    public ChannelDTO getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    /**
     * @return CustomerDTO return the customer
     */
    public CustomerDTO getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    /**
     * @return DocumentProcessDTO return the documentProcess
     */
    public DocumentProcessDTO getDocumentProcess() {
        return documentProcess;
    }

    /**
     * @param documentProcess the documentProcess to set
     */
    public void setDocumentProcess(DocumentProcessDTO documentProcess) {
        this.documentProcess = documentProcess;
    }

    /**
     * @return ProductDTO return the product
     */
    public ProductDTO getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    /**
     * @return List<MetadataDTO> return the metadata
     */
    public List<MetadataDTO> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(List<MetadataDTO> metadata) {
        this.metadata = metadata;
    }

}