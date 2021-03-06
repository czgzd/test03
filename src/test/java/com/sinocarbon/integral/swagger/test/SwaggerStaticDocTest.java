//package com.sinocarbon.integral.swagger.test;
//
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.annotation.Resource;
//
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.github.robwin.markup.builder.MarkupLanguage;
//import io.github.robwin.swagger2markup.GroupBy;
//import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
//import com.sinocarbon.integral.MavenDemoApplication;
//import com.sinocarbon.integral.swagger.test.handler.MySwaggerResultHandler;
//
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = MavenDemoApplication.class)
//@WebAppConfiguration
//public class SwaggerStaticDocTest {
//
//	private String snippetDir = "target/generated-snippets";
//	private String outputDir = "target/asciidoc";
//	@Autowired
//	private MockMvc mockMvc;
//	@Resource
//	private ObjectMapper objectMapper;
//
//	@After
//	public void Test() throws Exception {
//		// ??????swagger.json,??????outputDir?????????
//		mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
//				.andDo(MySwaggerResultHandler.outputDirectory(outputDir).build()).andExpect(status().isOk())
//				.andReturn();
//
//		// ????????????????????????swagger.json??????asciiDoc,?????????outputDir
//		// ??????outputDir?????????????????????<generated></generated>??????????????????
//		Swagger2MarkupConverter.from(outputDir + "/swagger.json").withPathsGroupedBy(GroupBy.TAGS)// ???tag??????
//				.withMarkupLanguage(MarkupLanguage.ASCIIDOC)// ??????
//				.withExamples(snippetDir).build().intoFolder(outputDir);// ??????
//
//	}
//
//	@Test
//	public void exampleApi() throws Exception {
//
//		mockMvc.perform(get("/example/test?username=superadmin&tenantId=webapp")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andDo(MockMvcRestDocumentation.document("findUserInfoByUsername", preprocessResponse(prettyPrint())));
//	}
//
//}
