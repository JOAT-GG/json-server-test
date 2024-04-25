package com.example.demo;

import com.example.demo.domain.MatchResult;
import com.example.demo.repository.MatchResultQueryRepository;
import com.example.demo.repository.MatchResultRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

	private final MatchResultRepository matchResultRepository;
	private final MatchResultQueryRepository queryRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<MatchResult> matchResultList = mapper.readValue("[\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7041307770\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-23 13:45:18\",\n" +
				"        \"champion\": \"Sejuani\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7041234339\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-23 13:02:11\",\n" +
				"        \"champion\": \"XinZhao\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7037591300\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-20 15:51:31\",\n" +
				"        \"champion\": \"Gwen\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7037297606\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-20 13:37:19\",\n" +
				"        \"champion\": \"Gwen\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7037016692\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-20 11:15:30\",\n" +
				"        \"champion\": \"Nidalee\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7036976578\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-20 10:51:56\",\n" +
				"        \"champion\": \"Ashe\",\n" +
				"        \"position\": \"MIDDLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7034541267\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-18 14:33:52\",\n" +
				"        \"champion\": \"Elise\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7032304831\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-16 14:40:30\",\n" +
				"        \"champion\": \"Sejuani\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7030996380\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-15 13:03:53\",\n" +
				"        \"champion\": \"XinZhao\",\n" +
				"        \"position\": \"JUNGLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7028584520\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-13 16:59:03\",\n" +
				"        \"champion\": \"Aphelios\",\n" +
				"        \"position\": \"MIDDLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7028560153\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-13 16:38:20\",\n" +
				"        \"champion\": \"DrMundo\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7028520473\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-13 16:15:10\",\n" +
				"        \"champion\": \"Singed\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7028465829\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-13 15:46:34\",\n" +
				"        \"champion\": \"Vladimir\",\n" +
				"        \"position\": \"MIDDLE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7022826804\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-09 16:54:36\",\n" +
				"        \"champion\": \"Rakan\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7022806317\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-09 16:35:02\",\n" +
				"        \"champion\": \"Blitzcrank\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7022761295\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-09 16:07:17\",\n" +
				"        \"champion\": \"Pyke\",\n" +
				"        \"position\": \"TOP\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7022586775\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-09 14:42:12\",\n" +
				"        \"champion\": \"Ezreal\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7020223472\",\n" +
				"        \"win\": true,\n" +
				"        \"date\": \"2024-04-07 15:04:37\",\n" +
				"        \"champion\": \"Elise\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7018320915\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-06 13:41:26\",\n" +
				"        \"champion\": \"Viego\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"match_id\": \"KR_7018268722\",\n" +
				"        \"win\": false,\n" +
				"        \"date\": \"2024-04-06 13:16:10\",\n" +
				"        \"champion\": \"Belveth\",\n" +
				"        \"position\": \"NONE\"\n" +
				"    }\n" +
				"]", new TypeReference<List<MatchResult>>(){});

		matchResultRepository.saveAll(matchResultList);
		System.out.println("queryRepository.getTotalWinRate() = " + queryRepository.getTotalWinRate());
	}
}
