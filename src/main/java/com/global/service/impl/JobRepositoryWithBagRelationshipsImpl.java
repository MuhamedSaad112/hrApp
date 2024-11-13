//package com.global.service.impl;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//
//import com.global.entity.Job;
//import com.global.repository.JobRepositoryWithBagRelationships;
//
//import jakarta.persistence.EntityManager;
//
//public class JobRepositoryWithBagRelationshipsImpl implements JobRepositoryWithBagRelationships {
//
//	@Autowired
//	private EntityManager entityManager;
//
//	@Override
//	public Optional<Job> fetchBagRelationships(Optional<Job> job) {
//		return job.map(this::fetchTasks);
//	}
//
//	@Override
//	public Page<Job> fetchBagRelationships(Page<Job> jobs) {
//		return new PageImpl<>(fetchBagRelationships(jobs.getContent()), jobs.getPageable(), jobs.getTotalElements());
//	}
//
//	@Override
//	public List<Job> fetchBagRelationships(List<Job> jobs) {
//		return Optional.of(jobs).map(this::fetchTasks).get();
//	}
//
//	Job fetchTasks(Job result) {
//		return entityManager
//				.createQuery("select job from Job job left join fetch job.tasks where job is :job", Job.class)
//				.setParameter("job", result).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
//	}
//
//	List<Job> fetchTasks(List<Job> jobs) {
//		return entityManager
//				.createQuery("select distinct job from Job job left join fetch job.tasks where job in :jobs", Job.class)
//				.setParameter("jobs", jobs).setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
//	}
//
//}

package com.global.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.global.entity.Job;
import com.global.repository.JobRepositoryWithBagRelationships;

import jakarta.persistence.EntityManager;

public class JobRepositoryWithBagRelationshipsImpl implements JobRepositoryWithBagRelationships {

	@Autowired
	private EntityManager entityManager;

	// تحسين التعامل مع Optional بطريقة حديثة
	@Override
	public Optional<Job> fetchBagRelationships(Optional<Job> job) {
		return job.map(this::fetchTasks);
	}

	@Override
	public Page<Job> fetchBagRelationships(Page<Job> jobs) {
		List<Job> fetchedJobs = fetchBagRelationships(jobs.getContent());
		return new PageImpl<>(fetchedJobs, jobs.getPageable(), jobs.getTotalElements());
	}

	@Override
	public List<Job> fetchBagRelationships(List<Job> jobs) {
		return Optional.ofNullable(jobs).map(this::fetchTasks) // تأكد من عدم وجود قيم فارغة
				.orElse(List.of());
	}

	// تحسين استعلامات الـ "join fetch"
	private Job fetchTasks(Job job) {
		return entityManager
				.createQuery("select job from Job job left join fetch job.tasks where job = :job", Job.class)
				.setParameter("job", job).setHint("hibernate.query.passDistinctThrough", false) // استخدم التلميح بشكل
																								// صريح إذا لزم الأمر
				.getSingleResult();
	}

	private List<Job> fetchTasks(List<Job> jobs) {
		return entityManager
				.createQuery("select job from Job job left join fetch job.tasks where job in :jobs", Job.class)
				.setParameter("jobs", jobs).setHint("hibernate.query.passDistinctThrough", false).getResultList();
	}
}
