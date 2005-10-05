package uk.ltd.getahead.dwr.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.ltd.getahead.dwr.Creator;
import uk.ltd.getahead.dwr.CreatorManager;
import uk.ltd.getahead.dwr.Messages;
import uk.ltd.getahead.dwr.util.LocalUtil;
import uk.ltd.getahead.dwr.util.Logger;

/**
 * A class to manage the types of creators and the instansiated creators.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class DefaultCreatorManager implements CreatorManager
{
    /**
     * Set the debug status
     * @param debug The new debug setting
     */
    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#isDebug()
     */
    public boolean isDebug()
    {
        return debug;
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#addCreatorType(java.lang.String, java.lang.Class)
     */
    public void addCreatorType(String typeName, Class clazz)
    {
        if (!Creator.class.isAssignableFrom(clazz))
        {
            throw new IllegalArgumentException(Messages.getString("DefaultCreatorManager.CreatorNotAssignable", clazz.getName(), Creator.class.getName())); //$NON-NLS-1$
        }

        try
        {
            clazz.newInstance();
        }
        catch (InstantiationException ex)
        {
            throw new IllegalArgumentException(Messages.getString("DefaultCreatorManager.CreatorNotInstantiatable", clazz.getName(), ex.toString())); //$NON-NLS-1$
        }
        catch (IllegalAccessException ex)
        {
            throw new IllegalArgumentException(Messages.getString("DefaultCreatorManager.CreatorNotAccessable", clazz.getName(), ex.toString())); //$NON-NLS-1$
        }

        creatorTypes.put(typeName, clazz);
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#addCreator(java.lang.String, java.lang.String, java.util.Map)
     */
    public void addCreator(String typeName, String scriptName, Map params) throws InstantiationException, IllegalAccessException, IllegalArgumentException
    {
        Class clazz = (Class) creatorTypes.get(typeName);

        if (clazz == null)
        {
            log.error("Missing creator: " + typeName + " (while initializing creator for: " + scriptName + ".js)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            return;
        }

        Creator creator = (Creator) clazz.newInstance();

        LocalUtil.setParams(creator, params, ignore);
        creator.setProperties(params);

        // add the creator for the script name
        addCreator(scriptName, creator);
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#addCreator(java.lang.String, uk.ltd.getahead.dwr.Creator)
     */
    public void addCreator(String scriptName, Creator creator) throws IllegalArgumentException
    {
        // Check that we don't have this one already
        Creator other = (Creator) creators.get(scriptName);
        if (other != null)
        {
            throw new IllegalArgumentException(Messages.getString("DefaultCreatorManager.DuplicateName", scriptName, other.getType().getName(), creator)); //$NON-NLS-1$
        }

        // Check that it can at least tell us what type of thing we will be getting
        try
        {
            Class test = creator.getType();
            if (test == null)
            {
                log.error("Creator: '" + creator + "' for " + scriptName + ".js is returning null for type queries."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }
            else
            {
                creators.put(scriptName, creator);
            }
        }
        catch (NoClassDefFoundError ex)
        {
            log.error("Missing class for creator '" + creator + "'. Cause: " + ex.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        catch (Exception ex)
        {
            log.error("Error loading class for creator '" + creator + "'.", ex); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#getCreatorNames()
     */
    public Collection getCreatorNames() throws SecurityException
    {
        if (!debug)
        {
            throw new SecurityException();
        }

        return Collections.unmodifiableSet(creators.keySet());
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#getCreator(java.lang.String)
     */
    public Creator getCreator(String scriptName) throws SecurityException
    {
        Creator creator = (Creator) creators.get(scriptName);
        if (creator == null)
        {
            StringBuffer buffer = new StringBuffer("Names of known classes are: "); //$NON-NLS-1$
            for (Iterator it = creators.keySet().iterator(); it.hasNext();)
            {
                String key = (String) it.next();
                buffer.append(key);
                buffer.append(' ');
            }

            log.warn(buffer.toString());
            throw new SecurityException(Messages.getString("DefaultCreatorManager.MissingName", scriptName)); //$NON-NLS-1$
        }

        return creator;
    }

    /* (non-Javadoc)
     * @see uk.ltd.getahead.dwr.CreatorManager#setCreators(java.util.Map)
     */
    public void setCreators(Map creators)
    {
        this.creators = creators;
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(DefaultCreatorManager.class);

    /**
     * The list of the available creators
     */
    private Map creatorTypes = new HashMap();

    /**
     * The list of the configured creators
     */
    private Map creators = new HashMap();

    /**
     * Are we in debug mode?
     */
    private boolean debug = false;

    /**
     * The properties that we don't warn about if they don't exist.
     * @see DefaultCreatorManager#addCreator(String, String, Map)
     */
    private static List ignore = Arrays.asList(new String[] { "creator", "class" }); //$NON-NLS-1$ //$NON-NLS-2$
}
