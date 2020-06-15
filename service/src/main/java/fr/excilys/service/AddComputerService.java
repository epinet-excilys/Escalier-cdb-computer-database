package fr.excilys.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.dto.AddAndEditComputerParameterDTO;
import fr.excilys.dto.CompanyDTO;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ValidatorException;
import fr.excilys.mapper.CompanyMapper;
import fr.excilys.mapper.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.validator.Validator;

@Service
public class AddComputerService {
	
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper computerMapper;
	private Validator validator;
	
	public AddComputerService(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, Validator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}
	
	public List<CompanyDTO> getCompanyDTOListInModelView() {
		List<Company> companyList = companyService.getAllCompany();

		return companyList.stream().map(CompanyMapper::fromCompanyToCompanyDTO)
				.collect(toList());
	}
	
	public void  addComputerInService(AddAndEditComputerParameterDTO addAndEditComputerParameterDTO) throws ValidatorException{

		validator.validateFields(
				addAndEditComputerParameterDTO.getComputerName(),
				addAndEditComputerParameterDTO.getIntroduced(),
				addAndEditComputerParameterDTO.getDiscontinued(),
				addAndEditComputerParameterDTO.getCompanyId());
		
			CompanyDTO companyDTO = new CompanyDTO();
			if (!addAndEditComputerParameterDTO.getCompanyId().isBlank()) {
				companyDTO.setId(Integer.parseInt(addAndEditComputerParameterDTO.getCompanyId()));
			}
			
			ComputerDTO computerDTO = new ComputerDTO(
					addAndEditComputerParameterDTO.getComputerName(),
					addAndEditComputerParameterDTO.getIntroduced(),
					addAndEditComputerParameterDTO.getDiscontinued(),
					companyDTO);
			
			Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);
			computerService.add(computer);

	}

	
	
	
	
	
	

}
