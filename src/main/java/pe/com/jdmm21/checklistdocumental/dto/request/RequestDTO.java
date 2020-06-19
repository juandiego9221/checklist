package pe.com.jdmm21.checklistdocumental.dto.request;

import java.util.List;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;

public class RequestDTO {
    private BranchDTO branchDTO;
    private ChannelDTO channelDTO;
    private CustomerDTO customerDTO;
    private DocumentProcessDTO documentProcessDTO;
    private ProductDTO productDTO;
    private List<MetadataDTO> metadataDTO;

    /**
     * @return BranchDTO return the branchDTO
     */
    public BranchDTO getBranchDTO() {
        return branchDTO;
    }

    /**
     * @param branchDTO the branchDTO to set
     */
    public void setBranchDTO(BranchDTO branchDTO) {
        this.branchDTO = branchDTO;
    }

    /**
     * @return ChannelDTO return the channelDTO
     */
    public ChannelDTO getChannelDTO() {
        return channelDTO;
    }

    /**
     * @param channelDTO the channelDTO to set
     */
    public void setChannelDTO(ChannelDTO channelDTO) {
        this.channelDTO = channelDTO;
    }

    /**
     * @return CustomerDTO return the customerDTO
     */
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    /**
     * @param customerDTO the customerDTO to set
     */
    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    /**
     * @return DocumentProcessDTO return the documentProcessDTO
     */
    public DocumentProcessDTO getDocumentProcessDTO() {
        return documentProcessDTO;
    }

    /**
     * @param documentProcessDTO the documentProcessDTO to set
     */
    public void setDocumentProcessDTO(DocumentProcessDTO documentProcessDTO) {
        this.documentProcessDTO = documentProcessDTO;
    }

    /**
     * @return ProductDTO return the productDTO
     */
    public ProductDTO getProductDTO() {
        return productDTO;
    }

    /**
     * @param productDTO the productDTO to set
     */
    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    /**
     * @return List<MetadataDTO> return the metadataDTO
     */
    public List<MetadataDTO> getMetadataDTO() {
        return metadataDTO;
    }

    /**
     * @param metadataDTO the metadataDTO to set
     */
    public void setMetadataDTO(List<MetadataDTO> metadataDTO) {
        this.metadataDTO = metadataDTO;
    }

    @Override
    public String toString() {
        return "RequestDTO [branchDTO=" + branchDTO + ", channelDTO=" + channelDTO + ", customerDTO=" + customerDTO
                + ", documentProcessDTO=" + documentProcessDTO + ", metadataDTO=" + metadataDTO + ", productDTO="
                + productDTO + "]";
    }

}