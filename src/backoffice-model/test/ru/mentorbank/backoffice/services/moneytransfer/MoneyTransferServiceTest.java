package ru.mentorbank.backoffice.services.moneytransfer;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.dao.exception.OperationDaoException;
import ru.mentorbank.backoffice.model.Operation;
import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.model.transfer.JuridicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.PhysicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.TransferRequest;
import ru.mentorbank.backoffice.services.accounts.AccountService;
import ru.mentorbank.backoffice.services.accounts.AccountServiceBean;
import ru.mentorbank.backoffice.services.moneytransfer.exceptions.TransferException;
import ru.mentorbank.backoffice.services.stoplist.StopListService;
import ru.mentorbank.backoffice.services.stoplist.StopListServiceStub;
import ru.mentorbank.backoffice.test.AbstractSpringTest;
import ru.mentorbank.backoffice.dao.stub.OperationDaoStub;

public class MoneyTransferServiceTest extends AbstractSpringTest {

	@Autowired
	private MoneyTransferServiceBean moneyTransferService;
	private StopListService mockedStopListService;
	private AccountService mockedAccountService;
	private OperationDaoStub mockedOperationDao;
	
	@Before
	public void setUp() {
		mockedStopListService = mock(StopListServiceStub.class);
		mockedAccountService = mock(AccountServiceBean.class);
		mockedOperationDao = mock(OperationDaoStub.class);
		 
	}

	@Test
	public void transfer() throws TransferException, OperationDaoException {
		//fail("not implemented yet");
		// TODO: Необходимо протестировать, что для хорошего перевода всё
		// работает и вызываются все необходимые методы сервисов
		// Далее следует закоментированная закотовка
		 
		 TransferRequest request = new TransferRequest();
		 
		 JuridicalAccountInfo srcAccountInfo = new JuridicalAccountInfo();
		 PhysicalAccountInfo dstAccountInfo = new PhysicalAccountInfo();
		 
		 
		 srcAccountInfo.setAccountNumber("111111111111111");
		 srcAccountInfo.setInn(StopListServiceStub.INN_FOR_OK_STATUS);
		 
		 dstAccountInfo.setDocumentNumber("987678");
		 dstAccountInfo.setDocumentSeries("9876");
		 
		 request.setSrcAccount(srcAccountInfo);
		 request.setDstAccount(dstAccountInfo);
		 
		 moneyTransferService.setAccountService(mockedAccountService);
		 moneyTransferService.setStopListService(mockedStopListService);
		 moneyTransferService.setOperationDao(mockedOperationDao);
		 
		 when(mockedAccountService.verifyBalance(srcAccountInfo)).thenReturn(true);
		 StopListInfo okStopListInfo = new StopListInfo();
		 okStopListInfo.setStatus(StopListStatus.OK);
		 when(mockedStopListService.getJuridicalStopListInfo(any(JuridicalStopListRequest.class))).thenReturn(okStopListInfo);
		 when(mockedStopListService.getPhysicalStopListInfo(any(PhysicalStopListRequest.class))).thenReturn(okStopListInfo);
		 
		 
		 moneyTransferService.transfer(request);
		 verify(mockedStopListService).getJuridicalStopListInfo(any(JuridicalStopListRequest.class));
		 verify(mockedAccountService).verifyBalance(srcAccountInfo);
		 verify(mockedOperationDao).saveOperation(any(Operation.class));
		 
	}
}
