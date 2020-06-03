package fr.excilys.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

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
	
	public void  addComputerInService(String computerName, String introduced,
			String discontinued,String companyId) throws ValidatorException{

			validator.validateFields(computerName, introduced, discontinued, companyId);
			CompanyDTO companyDTO = new CompanyDTO();
			if (!companyId.isBlank()) {
				companyDTO.setId(Integer.parseInt(companyId));
			}
			ComputerDTO computerDTO = new ComputerDTO(computerName, introduced, discontinued, companyDTO);
			Computer computer = computerMapper.fromComputerDTOToComputer(computerDTO);
			computerService.add(computer);

	}
	
	
	
	
	
	

}
