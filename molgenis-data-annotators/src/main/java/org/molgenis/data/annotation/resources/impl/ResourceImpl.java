package org.molgenis.data.annotation.resources.impl;

import java.io.File;
import java.util.Iterator;

import org.molgenis.data.Entity;
import org.molgenis.data.Query;
import org.molgenis.data.Repository;
import org.molgenis.data.annotation.resources.Resource;
import org.molgenis.data.annotation.resources.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link Resource}, a file-based repository. The location of the file is configured in
 * MolgenisSettings.
 */
public class ResourceImpl implements Resource
{
	private final String name;
	private final RepositoryFactory repositoryFactory;
	private final ResourceConfig config;
	// the file the current repository works on
	private volatile File file;
	// the current repository
	private volatile Repository repository;

	private static final Logger LOG = LoggerFactory.getLogger(ResourceImpl.class);

	/**
	 * Creates a new {@link Resource}
	 * 
	 * @param name
	 *            the name of the Resource
	 * @param config
	 *            ResourceConfig that configure the location of the file
	 * @param repositoryFactory
	 *            Factory used to create the Repository when the file becomes available
	 */
	public ResourceImpl(String name, ResourceConfig config, RepositoryFactory repositoryFactory)
	{
		this.name = name;
		this.config = config;
		this.repositoryFactory = repositoryFactory;
	}

	/**
	 * Indicates if the resource is available.
	 * 
	 * @return indication if this resource is available
	 */
	@Override
	public synchronized boolean isAvailable()
	{
		if (repository != null && needsRefresh())
		{
			repository = null;
			file = null;
		}
		final File file = getFile();
		return file != null && file.exists();
	}

	/**
	 * Searches the repository if it is available.
	 * 
	 * @param q
	 *            the {@link Query} to use
	 * @return {@link Entity}s found
	 * @throws NullPointerException
	 *             if the repository is not available
	 */
	@Override
	public Iterable<Entity> findAll(Query q)
	{
		return new Iterable<Entity>()
		{
			@Override
			public Iterator<Entity> iterator()
			{
				return getRepository().findAll(q).iterator();
			}
		};
	}

	private boolean needsRefresh()
	{
		File newFile = config.getFile();
		boolean needsRefresh = file != null && !file.equals(newFile);
		if (needsRefresh)
		{
			repository = null;
			file = null;
		}
		return repository == null;
	}

	private Repository getRepository()
	{
		if (repository == null && isAvailable())
		{
			initialize();
		}
		return repository;
	}

	private synchronized void initialize()
	{
		if (isAvailable() && repository == null)
		{
			try
			{
				file = getFile();
				if (file != null)
				{
					repository = repositoryFactory.createRepository(file);
				}
			}
			catch (Exception e)
			{
				LOG.warn("Resource {} failed to create Repository for file {}.", name, file, e);
			}
		}
	}

	private synchronized File getFile()
	{
		return config.getFile();
	}

	@Override
	public String getName()
	{
		return name;
	}

}
