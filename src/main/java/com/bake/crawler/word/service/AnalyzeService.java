package com.bake.crawler.word.service;

import java.util.Map;
import java.util.Set;

public interface AnalyzeService {

	Set<Map<String, Object>> analyze() throws Exception;
}
