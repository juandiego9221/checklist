package pe.com.jdmm21.checklistdocumental.lib.lib004;

import java.util.List;
import java.util.Map;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.BranchDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.ChannelDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.ProductDTO;

public interface RulesInventory {
    public String executeGetDocumentTypes(Map<String, String> inputVersion, ProductDTO productDTO, BranchDTO branchDTO,
            ChannelDTO channelDTO, List<MetadataDTO> metadata);

}