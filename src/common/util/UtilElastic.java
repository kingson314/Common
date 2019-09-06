package common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import common.util.collection.UtilCollection;


public class UtilElastic {
//    public static String IndexName_app="app-base";//6.0后一个index下面只能有一个type
	private static Map<String,Object>CfgElastic=UtilCollection.getMap("clusterName","app-infoCenter","hostName","es","port","9300","enabled","true");
    public static void init(String indexName_) {
        try {
            if(!UtilElastic.isExistIndex(indexName_)) {
                System.out.println(indexName_);
                UtilElastic.createIndex(indexName_,5,0);
            }
//			XContentBuilder source = XContentFactory.jsonBuilder()
//	                .startObject()
//	                .startObject("properties")
//	                .startObject("id")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("type")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("name")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("title")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("content")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("createDate")
//	                .field("type", "text")
//	                .endObject()
//	                .endObject()
//	                .endObject();
//			UtilElastic.setMapping(indexName,IndexType_patent,source);
//
//
//			indexName=IndexName_app+IndexType_technology;
//	    	indexName=IndexType_technology;
//	    	if(!UtilElastic.isExistIndex(indexName)) {
//	    		System.out.println(indexName);
//	    		UtilElastic.createIndex(indexName,5,0);
//	    	}
//			XContentBuilder source1 = XContentFactory.jsonBuilder()
//	                .startObject()
//	                .startObject("properties")
//	                .startObject("id")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("type")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("name")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("title")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("content")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("createDate")
//	                .field("type", "text")
//	                .endObject()
//	                .endObject()
//	                .endObject();
//			UtilElastic.setMapping(indexName,IndexType_technology,source1);
        }catch(Exception e) {
//			e.printStackTrace();
        }
    }

    //TransportClient对象，用于连接ES集群
    private static volatile TransportClient client;
    /**
     * 同步synchronized(*.class)代码块的作用和synchronized static方法作用一样,
     * 对当前对应的*.class进行持锁,static方法和.class一样都是锁的该类本身,同一个监听器
     * @return
     * @throws UnknownHostException
     */
    @SuppressWarnings({ "resource" })
    public static TransportClient getClient(){
        if(client==null){
            synchronized (TransportClient.class){
                try {
                	 
                     String clusterName=CfgElastic.get("clusterName").toString();
                     String hostName=CfgElastic.get("hostName").toString();
                     int tcpPort = Integer.valueOf(CfgElastic.get("port").toString());
                     Settings settings = Settings.builder().put("clusterName", clusterName).build();
                     client=new PreBuiltTransportClient(settings)
                             .addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), tcpPort));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        return client;
    }
    /**
     * 获取索引管理的IndicesAdminClient
     */
    private static IndicesAdminClient getAdminClient() {
        return getClient().admin().indices();
    }
    /**
     * 判定索引是否存在
     * @param indexName
     * @return
     */
    public static boolean isExistIndex(String indexName){
        IndicesExistsResponse response=getAdminClient().prepareExists(indexName).get();
        return response.isExists()?true:false;
    }
    /**
     * 判断指定的索引的类型是否存在
     *
     * @param indexName
     *            索引名
     * @param indexType
     *            索引类型
     * @return 存在：true; 不存在：false;
     */
    public static boolean isExistsType(String indexName, String indexType) {
        TypesExistsResponse response = getAdminClient().typesExists(new TypesExistsRequest(new String[] { indexName }, indexType))
                .actionGet();
        return response.isExists();
    }
    /**
     * 创建索引
     * @param indexName 索引名
     * @param shards   分片数 5
     * @param replicas  副本数 0
     * @return
     */
    public static boolean createIndex(String indexName, int shards, int replicas) {
        Settings settings = Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .build();
        CreateIndexResponse createIndexResponse = getAdminClient()
                .prepareCreate(indexName.toLowerCase())
                .setSettings(settings)
                .execute().actionGet();
        return createIndexResponse.isAcknowledged()?true:false;
    }
    /**
     * 位索引indexName设置mapping
     * @param indexName
     * @param indexType
     */
    public static void setMapping(String indexName, String indexType, XContentBuilder source) {
        getAdminClient().preparePutMapping(indexName)
                .setType(indexType)
                .setSource(source)
                .get();
    }
    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public static boolean deleteIndex(String indexName) {
        getAdminClient().prepareDelete(indexName.toLowerCase())
                .execute()
                .actionGet();
        return true;
    }
    /******************记录查询******************/
    /**
     * 根据ID查询
     * @param indexName
     * @param indexType
     * @param id
     * @throws Exception
     */
    public static void get(String indexName,String indexType,String id) throws Exception{
        GetResponse response = getClient().prepareGet(indexName, indexType, id)
                .get();
        System.out.println(response.getSourceAsString());
    }
    /**
     *
     * @param list
     * @throws Exception
     */
    public static void getMulti(List<Map<String,String>> list) throws Exception{
        MultiGetRequestBuilder builder = getClient().prepareMultiGet();
        for(Map<String,String>map:list) {
            String indexName=map.get("indexName");
            String indexType=map.get("indexType");
            String id=map.get("id");
            builder.add(indexName,indexType,id);
        }
        MultiGetResponse multiGetResponse =builder.get();
        for (MultiGetItemResponse itemResponse : multiGetResponse) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String sourceAsString = response.getSourceAsString();
                System.out.println(sourceAsString);
            }
        }
    }
    /**
     * 查看集群信息
     */
    public void getInfo() {
        List<DiscoveryNode> nodes = getClient().connectedNodes();
        for (DiscoveryNode node : nodes) {
            System.out.println(node.getHostAddress());
        }
    }

    /**
     * 查询所有 demo
     */
    public void searchAll(String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();//查询所有
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     * 分页查询 demo
     */
    public void searchPaging(int pageIndex,int pageSize,String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery()).setFrom(pageIndex).setSize(pageSize).execute().actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     * 排序查询 demo
     */
    public void searchOrderBy(List<String[]>listSort,String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        SearchRequestBuilder builder=srb.setQuery(QueryBuilders.matchAllQuery());
        for(String[] arr:listSort) {
            if("0".equals(arr[1])) {
                builder.addSort(arr[0],SortOrder.ASC);
            }else {
                builder.addSort(arr[0],SortOrder.DESC);
            }
        }
        SearchResponse sr=builder.execute().actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     * 数据列过滤查询 demo
     */
    public void searchInclude(String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
                .setFetchSource(new String[] {"title","price"},null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /***
     * 简单条件查询 demo
     */
    public void searchByCondition(String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title","铁"))
                .setFetchSource(new String[] {"title","price"},null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     * 条件查询高亮实现 demo
     */
    public void searchHighlight1(String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.preTags("<h2>");
        highlightBuilder.postTags("</h2>");
        highlightBuilder.field("title");
        SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title","战"))
                .highlighter(highlightBuilder)
                .setFetchSource(new String[] {"title","price"},null)
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            System.out.println(hit.getHighlightFields());
        }
    }
    /**
     * 多条件查询  must   demo
     */
    public void searchMutil(String indexName,String... indexType) {
        SearchRequestBuilder srb =getClient().prepareSearch(indexName).setTypes(indexType);
        MatchPhraseQueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        MatchPhraseQueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .must(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     * 多条件查询  mustNot
     */
    public void searchMutil2(String indexName,String... indexType) {
        SearchRequestBuilder srb =getClient().prepareSearch(indexName).setTypes(indexType);
        MatchPhraseQueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        MatchPhraseQueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "武士");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .mustNot(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     * 多条件查询  should提高得分
     * @throws Exception
     */
    public void searchMutil3(String indexName,String... indexType) {
        SearchRequestBuilder srb=getClient().prepareSearch(indexName).setTypes(indexType);
        MatchPhraseQueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        MatchPhraseQueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "星球");
        RangeQueryBuilder queryBuilder3=QueryBuilders.rangeQuery("publishDate").gt("2018-01-01");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .should(queryBuilder2)
                .should(queryBuilder3))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getScore()+":"+hit.getSourceAsString());
        }
    }
    /***
     * 多条件查询 range限制范围
     */
    public void searchMutil4(String indexName,String... indexType) {
        SearchRequestBuilder srb = getClient().prepareSearch(indexName).setTypes(indexType);
        MatchPhraseQueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "战");
        RangeQueryBuilder queryBuilder2=QueryBuilders.rangeQuery("price").lte(40);
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .filter(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }


    /******************记录操作******************/
    /**
     * 新增一条记录
     * @param indexName
     * @param indexType
     * @param map 必须包含ID字段
     * @return
     * @throws IOException
     */
    public static int save(String indexName,String indexType,Map<String,Object>map) throws IOException{
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject();
        for(Map.Entry<String, Object> entry:map.entrySet()) {
            source.field(entry.getKey(),entry.getValue());
        }
        source.endObject();
        // 存json入索引中
        IndexResponse response = getClient().prepareIndex(indexName, indexType, map.get("id").toString()).setSource(source).get();
        return response.status().getStatus();
    }

    /**
     * 更新一条记录
     * @param indexName
     * @param indexType
     * @param map 必须包含ID字段
     * @return
     * @throws IOException
     */
    public static int update(String indexName,String indexType,Map<String,Object>map) throws Exception{
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.type(indexType);
        updateRequest.id(map.get("id").toString());
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject();
        for(Map.Entry<String, Object> entry:map.entrySet()) {
            source.field(entry.getKey(),entry.getValue());
        }
        source.endObject();
        updateRequest.doc(source);
        UpdateResponse response = getClient().update(updateRequest).get();
        return response.status().getStatus();
    }

    public static int  saveOrUpdate(String indexName,String indexType,Map<String,Object>map) throws Exception{
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject();
        for(Map.Entry<String, Object> entry:map.entrySet()) {
            source.field(entry.getKey(),entry.getValue());
        }
        source.endObject();
        // 设置查询条件, 查找不到则添加生效
        IndexRequest indexRequest = new IndexRequest(indexName, indexType, map.get("id").toString())
                .source(source);
        // 设置更新, 查找到更新下面的设置
        UpdateRequest upsert = new UpdateRequest(indexName, indexType, map.get("id").toString())
                .doc(source)
                .upsert(indexRequest);
        UpdateResponse response = getClient().update(upsert).get();
        return response.status().getStatus();
    }

    /**
     * 根据ID删除记录
     * @param indexName
     * @param indexType
     * @param id
     * @return
     * @throws Exception
     */
    public static int delete(String indexName,String indexType,String id) throws Exception{
        DeleteResponse response = getClient().prepareDelete(indexName, indexType, id)
                .get();
        return response.status().getStatus();
    }

    /***********批量***********/
    /**
     * bulk 批量执行
     * 一次查询可以update 或 delete多个document
     */
    public static int saveBulk(String indexName,String indexType,List<Map<String,Object>>list) throws Exception{
        BulkRequestBuilder bulkRequest = getClient().prepareBulk();
        for(Map<String,Object>map:list) {
            XContentBuilder source = XContentFactory.jsonBuilder()
                    .startObject();
            for(Map.Entry<String, Object> entry:map.entrySet()) {
                source.field(entry.getKey(),entry.getValue());
            }
            source.endObject();
            // 存json入索引中
            IndexRequestBuilder builder = getClient().prepareIndex(indexName, indexType, map.get("id").toString()).setSource(source);

            bulkRequest.add(builder);
        }
        BulkResponse response = bulkRequest.get();
        return response.status().getStatus();
    }
    public static int updateBulk(String indexName,String indexType,List<Map<String,Object>>list) throws Exception{
        BulkRequestBuilder bulkRequest = getClient().prepareBulk();
        for(Map<String,Object>map:list) {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(indexName);
            updateRequest.type(indexType);
            updateRequest.id(map.get("id").toString());
            XContentBuilder source = XContentFactory.jsonBuilder()
                    .startObject();
            for(Map.Entry<String, Object> entry:map.entrySet()) {
                source.field(entry.getKey(),entry.getValue());
            }
            source.endObject();
            updateRequest.doc(source);
            bulkRequest.add(updateRequest);
        }
        BulkResponse response = bulkRequest.get();
        return response.status().getStatus();
    }
    public static int deleteBulk(String indexName,String indexType,List<Map<String,Object>>list) throws Exception{
        BulkRequestBuilder bulkRequest = getClient().prepareBulk();
        for(Map<String,Object>map:list) {
            bulkRequest.add(getClient().prepareDelete(indexName, indexType, map.get("id").toString()));
        }
        BulkResponse response = bulkRequest.get();
        return response.status().getStatus();
    }


//  public void testBulkProcessor() throws Exception {
//  // 创建BulkPorcessor对象
//  BulkProcessor bulkProcessor = BulkProcessor.builder(client, new Listener() {
//      public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
//          // TODO Auto-generated method stub
//      }
//      // 执行出错时执行
//      public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
//          // TODO Auto-generated method stub
//      }
//
//      public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
//          // TODO Auto-generated method stub
//      }
//  })
//  // 1w次请求执行一次bulk
//  .setBulkActions(10000)
//  // 1gb的数据刷新一次bulk
//  .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
//  // 固定5s必须刷新一次
//  .setFlushInterval(TimeValue.timeValueSeconds(5))
//  // 并发请求数量, 0不并发, 1并发允许执行
//  .setConcurrentRequests(1)
//  // 设置退避, 100ms后执行, 最大请求3次
//  .setBackoffPolicy(
//          BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
//  .build();
//
//  // 添加单次请求
//  bulkProcessor.add(new IndexRequest("twitter", "tweet", "1"));
//  bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));
//
//  // 关闭
//  bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
//  // 或者
//  bulkProcessor.close();
//}

    //    @Test
//    public void deleteIndex() {
//    	String indexName="app-trade";
//    	deleteIndex(indexName);
//    }
    public static void main(String[] args) {
//    	try {
//	    	String indexName="app-trade";
//	    	deleteIndex(indexName);
//	    	if(1==1)return;
//	    	String indexType="patent";
//	    	if(!isExistIndex(indexName)) {
//	    		System.out.println(indexName);
//	    		createIndex(indexName,5,0);
//	    	}
//	    	XContentBuilder source = XContentFactory.jsonBuilder()
//	                .startObject()
//	                .startObject("properties")
//	                .startObject("id")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("name")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("title")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("content")
//	                .field("type", "text")
//	                .endObject()
//	                .startObject("createDate")
//	                .field("type", "text")
//	                .endObject()
//	                .endObject()
//	                .endObject();
//    		System.out.println(source.toString());
//    		setMapping(indexName,indexType,source);
//    		save(indexName,indexType,UtilCollection.getMap("id","222","name","fengguoqu","type","aaa","title","1","content","2","createDate",UtilConver.dateToStr(Const.fm_yyyy_MM_dd_HH_mm_ss)));
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    	}
    }
}
