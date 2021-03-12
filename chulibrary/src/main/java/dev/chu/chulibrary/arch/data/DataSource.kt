package dev.chu.chulibrary.arch.data

import androidx.annotation.WorkerThread

/**
 * [RemoteDataSource], [LocalDataSource] 구현을 위한 Basic 인터페이스
 * [DataSource] 내 구혀되어질 함수들은 모두 [WorkerThread] 상에서 동작하도록 한다.
 * [dev.chu.chulibrary.concurrent.AppDispatchers.Default],
 * [dev.chu.chulibrary.concurrent.AppDispatchers.IO] 와 같은 coroutine 에서 실행시킨다.
 */
interface DataSource <Request, Response> {
    /**
     * Remote 또는 Local 저상소로 부터 데이터를 fetch 한다
     * @param isForceRefresh: true 면 사용자가 의도한 refresh 요청
     */
    suspend fun fetchData(request: Request, isForceRefresh: Boolean): Response?
}

/**
 * Api 등 Network 연결 기반 데이터를 다루는 인터페이스
 * [BaseRepository] 상에서 [RemoteDataSource] 를 통해 Api 를 이용한 데이터를 다룬다.
 */
interface RemoteDataSource<Request, Response> : DataSource<Request, Response>


/**
 * SqlLite, 파일등 로컬 환경 기반 데이터를 다루는 인터페이스
 * [BaseRepository] 상에서 [LocalDataSource] 를 통해 Local 데이터를 다룬다.
 */
interface LocalDataSource<Request, Response> : DataSource<Request, Response> {
    /**
     * [RemoteDataSource]를 통해 받아온 [response]를 Local 저장소에 저장할 때 사용한다.
     */
    suspend fun insertData(request: Request, response: Response)
}

